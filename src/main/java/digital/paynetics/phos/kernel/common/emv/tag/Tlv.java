package digital.paynetics.phos.kernel.common.emv.tag;

import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import digital.paynetics.phos.exceptions.TlvException;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;
import hirondelle.date4j.DateTime;

import static digital.paynetics.phos.kernel.common.misc.PhosMessageFormat.format;


/**
 * Tag and length value
 */
public class Tlv {
    /**
     * Tag
     */
    @SerializedName("tag")
    private final EmvTag tag;
    /**
     * Value in bytes
     */
    @SerializedName("value_bytes")
    private final byte[] valueBytes;
    /**
     * Tag length
     */
    @SerializedName("length")
    private final int length;


    @SerializedName("unknown_tag_bytes")
    private final byte[] unknownTagBytes;

    /**
     * @param tag
     * @param length     contains the number of value bytes (parsed from the rawEncodedLengthBytes)
     *                   the raw encoded length bytes
     * @param valueBytes
     */
    public Tlv(final EmvTag tag, final int length, final byte[] valueBytes) {
        this(tag, length, valueBytes, null);
    }


    public Tlv(EmvTag tag, int length, byte[] valueBytes, byte[] unknownTagBytes) {
        if (valueBytes == null || length != valueBytes.length) {
            throw new IllegalArgumentException("length != bytes.length");
        }


        this.tag = tag;
        this.length = length;
        this.unknownTagBytes = unknownTagBytes;

        byte[] xorKey = computeXorKey(valueBytes.length);
        this.valueBytes = new byte[valueBytes.length];
        for (int i = 0; i < valueBytes.length; i++) {
            this.valueBytes[i] = (byte) (xorKey[i] ^ valueBytes[i]);
        }
    }


    /**
     * Method used to get the field tag
     *
     * @return the tag
     */
    public EmvTag getTag() {
        return tag;
    }


    /**
     * Method used to get the field valueBytes
     *
     * @return the valueBytes
     */
    public byte[] getValueBytes() {
        byte[] xorKey = computeXorKey(valueBytes.length);
        byte[] ret = new byte[valueBytes.length];
        for (int i = 0; i < valueBytes.length; i++) {
            ret[i] = (byte) (xorKey[i] ^ valueBytes[i]);
        }

        return ret;
    }


    /**
     * Method used to get the field length
     *
     * @return the length
     */
    public int getLength() {
        return length;
    }


    /**
     * Get tag bytes
     *
     * @return tag bytes
     */
    public byte[] getTagBytes() {
        return tag.getTagBytes();
    }


    /**
     * Returns TLV value as integer
     * Expects the data to contain only digits. Example: 0x13 will be returned as 13
     *
     * @see #getValueAsHexInt()
     * @return integer value
     */
    public int getValueAsBcdInt() throws TlvException {
        String tmp = ByteUtils.toHexString(getValueBytes());

        try {
            return Integer.parseInt(tmp);
        } catch (NumberFormatException e) {
            throw new TlvException(format("Cannot parse Tlv {} as int: {}", getTag().getName(), tmp), e);
        }
    }


    /**
     * Gets the value converting the bytes to int. For example 0x0d will be returned as 13 (decimal)
     *
     * @return
     * @see #getValueAsBcdInt()
     */
    public int getValueAsHexInt() {
        byte[] tmp;
        byte[] src = getValueBytes();
        if (src.length > 4) {
            throw new IllegalArgumentException("Source length > 4");
        }
        if (src.length == 4) {
            tmp = src;
        } else {
            tmp = new byte[4];
        }

        System.arraycopy(src, 0, tmp, tmp.length - src.length, src.length);
        return ByteBuffer.wrap(tmp).getInt();
    }


    public long getValueAsHexLong() {
        byte[] tmp;
        byte[] src = getValueBytes();
        if (src.length > 8) {
            throw new IllegalArgumentException("Source length > 8");
        }
        if (src.length == 8) {
            tmp = src;
        } else {
            tmp = new byte[8];
        }

        System.arraycopy(src, 0, tmp, tmp.length - src.length, src.length);
        return ByteBuffer.wrap(tmp).getLong();
    }


    /**
     * Returns TLV value as String
     *
     * @return string value
     */
    public String getValueAsString() {
        return new String(getValueBytes());
    }


    /**
     * Returns TLV value as hex encoded string
     *
     * @return hex string
     */
    public String getValueAsHex() {
        return ByteUtils.toHexString(getValueBytes());
    }


    /**
     * @return
     */
    public DateTime getValueAsDate() {
        if (getValueBytes().length != 3) {
            throw new IllegalArgumentException("Data length must be 3");
        }

        String tmp = ByteUtils.toHexString(getValueBytes());

        String y2str = tmp.substring(0, 2);
        int y2 = Integer.parseInt(y2str);

        String finalDt;
        if (y2 > 50) {
            finalDt = "19" + y2str + "-" + tmp.substring(2, 4) + "-" + tmp.substring(4);
        } else {
            finalDt = "20" + y2str + "-" + tmp.substring(2, 4) + "-" + tmp.substring(4);
        }

        return new DateTime(finalDt);
    }


    public byte[] toByteArray() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            out.write(getTagBytes());
            out.write(TlvUtils.encodeTagLength(getValueBytes().length));
            out.write(getValueBytes());
        } catch (IOException e) {
            // cannot happen
            throw new RuntimeException(e);
        }

        return out.toByteArray();
    }


    public byte[] getUnknownTagBytes() {
        return unknownTagBytes;
    }


    public void purge() {
        Arrays.fill(valueBytes, (byte) 0);
    }


    private byte[] computeXorKey(int targetLength) {
        targetLength = targetLength < 2 ? 2 : targetLength;
        byte[] xorKey = new byte[targetLength];
        xorKey[0] = tag.getTagBytes()[0];
        if (tag.getTagBytes().length == 1) {
            xorKey[1] = (byte) (xorKey[0] ^ 0xb4); // 0xb4 magic value
        } else {
            xorKey[1] = tag.getTagBytes()[1];
        }

        xorKey[0] = (byte) (xorKey[0] ^ xorKey[1]);
        xorKey[1] = (byte) (xorKey[1] ^ 0x7c); // 0x7c magic value


        for (int i = 2; i < targetLength; i++) {
            xorKey[i] = (byte) ((xorKey[i - 2] + i * 44) ^ xorKey[i - 1]); // 44 magic value
        }

        return xorKey;
    }
}
