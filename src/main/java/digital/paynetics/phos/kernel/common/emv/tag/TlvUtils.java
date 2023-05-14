package digital.paynetics.phos.kernel.common.emv.tag;

import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import digital.paynetics.phos.exceptions.TlvException;
import digital.paynetics.phos.kernel.common.misc.ByteUtils;


/**
 * List of utils methods to manipulate Tlv
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public final class TlvUtils {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(TlvUtils.class);

    private TlvUtils() {
        throw new AssertionError("Non-instantiable utility class");
    }


    public static byte[] readTagIdBytes(final ByteArrayInputStream stream) {
        final ByteArrayOutputStream tagBAOS = new ByteArrayOutputStream();
        final byte tagFirstOctet = (byte) stream.read();
        tagBAOS.write(tagFirstOctet);

        // Find TAG bytes
        final byte MASK = (byte) 0x1F;
        if ((tagFirstOctet & MASK) == MASK) { // EMV book 3, Page 178 or Annex B1 (EMV4.3)
            // Tag field is longer than 1 byte
            do {
                final int nextOctet = stream.read();
                if (nextOctet < 0) {
                    break;
                }
                final byte tlvIdNextOctet = (byte) nextOctet;

                tagBAOS.write(tlvIdNextOctet);

                if (!ByteUtils.matchBitByBitIndex(tlvIdNextOctet, 7) || ByteUtils.matchBitByBitIndex(tlvIdNextOctet, 7)
                        && (tlvIdNextOctet & 0x7f) == 0) {
                    break;
                }
            } while (true);
        }
        return tagBAOS.toByteArray();
    }


    public static int readTagLength(final ByteArrayInputStream stream) throws TlvException {
        // Find LENGTH bytes
        final int length;
        int tmpLength = stream.read();

        if (tmpLength < 0) {
            throw new TlvException("Negative length: " + tmpLength);
        }

        if (tmpLength <= 127) { // 0111 1111
            // short length form
            length = tmpLength;
        } else if (tmpLength == 128) { // 1000 0000
            // length identifies indefinite form, will be set later
            // indefinite form is not specified in ISO7816-4, but we include it here for completeness
            length = tmpLength;
        } else {
            // long length form
            final int numberOfLengthOctets = tmpLength & 127; // turn off 8th bit
            tmpLength = 0;
            for (int i = 0; i < numberOfLengthOctets; i++) {
                final int nextLengthOctet = stream.read();
                if (nextLengthOctet < 0) {
                    throw new TlvException("EOS when reading length bytes");
                }
                tmpLength <<= 8;
                tmpLength |= nextLengthOctet;
            }
            length = tmpLength;
        }
        return length;
    }


    public static Tlv getNextTlv(byte[] data) throws TlvException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            return getNextTlv(stream);
        } catch (IOException e) {
            throw new AssertionError("Cannot happen");
        }
    }


    public static Tlv getNextTlv(byte[] data, boolean strict) throws TlvException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            return getNextTlv(stream, false, strict);
        } catch (IOException e) {
            throw new AssertionError("Cannot happen");
        }
    }

    public static Tlv getNextTlv(final ByteArrayInputStream stream) throws TlvException {
        return getNextTlv(stream, false);
    }


    public static Tlv getNextTlv(final ByteArrayInputStream stream, boolean allowFf) throws TlvException {
        return getNextTlv(stream, allowFf, false);
    }


    public static Tlv getNextTlv(final ByteArrayInputStream stream, boolean allowFf, boolean strict) throws TlvException {
        if (stream.available() < 2) {
            throw new TlvException("Error parsing data. Available bytes < 2 . Length=" + stream.available());
        }

        stream.mark(0);
        int peekInt = stream.read();
        byte peekByte = (byte) peekInt;

        if (!allowFf) {
            // ISO/IEC 7816 uses neither '00' nor 'FF' as tag value.
            // Before, between, or after Tlv-coded data objects,
            // '00' or 'FF' bytes without any meaning may occur
            // (for example, due to erased or modified Tlv-coded data objects).


            // peekInt == 0xffffffff indicates EOS
            while (peekInt != -1 && (peekByte == (byte) 0xFF || peekByte == (byte) 0x00)) {
                stream.mark(0); // Current position
                peekInt = stream.read();
                peekByte = (byte) peekInt;
            }
            stream.reset(); // Reset back to the last known position without 0x00 or 0xFF
        } else {
            // peekInt == 0xffffffff indicates EOS
            while (peekInt != -1 && (peekByte == (byte) 0x00)) {
                stream.mark(0); // Current position
                peekInt = stream.read();
                peekByte = (byte) peekInt;
            }
        }
        stream.reset(); // Reset back to the last known position without 0x00 or 0xFF

        if (stream.available() < 2) {
            throw new TlvException("Error parsing data. Available bytes < 2 . Length=" + stream.available());
        }

        final byte[] tagIdBytes = TlvUtils.readTagIdBytes(stream);

        // We need to get the raw length bytes.
        // Use quick and dirty workaround
        stream.mark(0);
        final int posBefore = stream.available();
        // Now parse the lengthbyte(s)
        // This method will read all length bytes. We can then find out how many bytes was read.
        int length = TlvUtils.readTagLength(stream); // Decoded
        // Now find the raw (encoded) length bytes
        final int posAfter = stream.available();
        stream.reset();
        final byte[] lengthBytes = new byte[posBefore - posAfter];

        if (lengthBytes.length < 1 || lengthBytes.length > 4) {
            throw new TlvException("Number of length bytes must be from 1 to 4. Found " + lengthBytes.length);
        }

        stream.read(lengthBytes, 0, lengthBytes.length);

        final int rawLength = ByteUtils.byteArrayToInt(lengthBytes);

        final byte[] valueBytes;

        byte[] unknownTagBytes = null;

        final EmvTag tag = EmvTag.resolveById(tagIdBytes);
        if (tag == EmvTag.UNKNOWN) {
            unknownTagBytes = tagIdBytes;
        }


        // Find VALUE bytes
        if (rawLength == 128) { // 1000 0000
            // indefinite form
            stream.mark(0);
            int prevOctet = 1;
            int curOctet;
            int len = 0;
            while (true) {
                len++;
                curOctet = stream.read();
                if (curOctet < 0) {
                    throw new TlvException("Error parsing data. Tlv " + "length byte indicated indefinite length, but EOS "
                            + "was reached before 0x0000 was found" + stream.available());
                }
                if (prevOctet == 0 && curOctet == 0) {
                    break;
                }
                prevOctet = curOctet;
            }
            len -= 2;
            valueBytes = new byte[len];
            stream.reset();
            stream.read(valueBytes, 0, len);
            length = len;
        } else {
            if (stream.available() < length) {
                throw new TlvException("Length byte(s) indicated " + length + " value bytes, but only " + stream.available()
                        + " " + (stream.available() > 1 ? "are" : "is") + " available");
            }

            if (strict && stream.available() > length) {
                throw new TlvException("Length byte(s) indicated " + length + " value bytes, but " + stream.available()
                        + " " + (stream.available() > 1 ? "are" : "is") + " available");
            }

            // definite form
            valueBytes = new byte[length];
            stream.read(valueBytes, 0, length);
        }

        // Remove any trailing 0x00 and 0xFF
        stream.mark(0);
        peekInt = stream.read();
        peekByte = (byte) peekInt;

        if (!allowFf) {
            while (peekInt != -1 && (peekByte == (byte) 0xFF || peekByte == (byte) 0x00)) {
                stream.mark(0);
                peekInt = stream.read();
                peekByte = (byte) peekInt;
            }
        } else {
            while (peekInt != -1 && (peekByte == (byte) 0x00)) {
                stream.mark(0);
                peekInt = stream.read();
                peekByte = (byte) peekInt;
            }
        }

        stream.reset(); // Reset back to the last known position without 0x00 or 0xFF

        return new Tlv(tag, length, valueBytes, unknownTagBytes);
    }


    /**
     * Method used to parser Tag and length
     *
     * @param data data to parse
     * @return list of tag and length
     */
    public static List<TagAndLength> parseTagAndLength(final byte[] data) throws TlvException {
        final List<TagAndLength> tagAndLengthList = new ArrayList<>();
        if (data != null) {
            final ByteArrayInputStream stream = new ByteArrayInputStream(data);

            while (stream.available() > 0) {
                if (stream.available() < 2) {
                    throw new TlvException("Data length < 2 : " + stream.available());
                }

                final EmvTag tag = EmvTag.resolveById(TlvUtils.readTagIdBytes(stream));
                final int tagValueLength = TlvUtils.readTagLength(stream);

                tagAndLengthList.add(new TagAndLength(tag, tagValueLength));
            }
        }
        return tagAndLengthList;
    }


    public static List<EmvTag> extractTagsList(final byte[] data) throws TlvException {
        final List<EmvTag> list = new ArrayList<>();
        if (data != null) {
            final ByteArrayInputStream stream = new ByteArrayInputStream(data);

            while (stream.available() > 0) {
                if (stream.available() < 1) {
                    throw new TlvException("Data length < 1 : " + stream.available());
                }

                final EmvTag tag = EmvTag.resolveById(TlvUtils.readTagIdBytes(stream));

                list.add(tag);
            }
        }
        return list;
    }


    /**
     * Method used to get Tag value
     *
     * @param data data
     * @param tag  tag to find
     * @return tag value or null
     */
    public static byte[] getValue(final byte[] data, final EmvTag... tag) throws TlvException {
        if (data != null) {
            final ByteArrayInputStream stream = new ByteArrayInputStream(data);

            while (stream.available() > 0) {

                final Tlv tlv = TlvUtils.getNextTlv(stream);

                if (tlv.getTag() == EmvTag.UNKNOWN) {
                    continue;
                }

                if (arrayContains(tag, tlv.getTag())) {
                    return tlv.getValueBytes();
                }
            }
        }

        return null;
    }


    /**
     * Method used to get length of all Tags
     *
     * @param list tag length list
     * @return the sum of tag length
     */
    public static int getLength(final List<TagAndLength> list) {
        int ret = 0;
        if (list != null) {
            for (final TagAndLength tl : list) {
                ret += tl.getLength();
            }
        }
        return ret;
    }


    public static boolean arrayContains(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind) != -1;
    }


    public static int indexOf(final Object[] array, final Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }


    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if (array == null) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return -1;
    }


    @SuppressWarnings("SpellCheckingInspection")
    public static List<Tlv> getTlvs(final byte[] data, final EmvTag... tag) throws TlvException {

        final List<Tlv> list = new ArrayList<>();

        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            while (stream.available() > 0) {
                final Tlv tlv = TlvUtils.getNextTlv(stream);
                if (arrayContains(tag, tlv.getTag())) {
                    list.add(tlv);
                } else if (tlv.getTag().isConstructed()) {
                    if (tlv.getTag() != EmvTag.UNKNOWN) {
                        list.addAll(TlvUtils.getTlvs(tlv.getValueBytes(), tag));
                    }
                }
            }
        } catch (IOException e) {
            throw new AssertionError("Cannot happen");
        }

        return list;
    }


    /**
     * Parses raw data into list of TLVs
     *
     * @param data
     * @return
     * @throws TlvException
     */
    public static List<Tlv> getTlvs(final byte[] data) throws TlvException {
        return getTlvs(data, false);
    }


    /**
     * Same as {{@link #getTlvs(byte[])}} but with a switch to allow tags starting with FF (as used by Mastercard)
     *
     * @param data
     * @param allowFfStartingTag if true will allow tags starting with FF (used by Mastercard proprietary tags)
     * @return
     * @throws TlvException
     */
    public static List<Tlv> getTlvs(final byte[] data, boolean allowFfStartingTag) throws TlvException {
        final List<Tlv> list = new ArrayList<>();

        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            while (stream.available() > 0) {
                final Tlv tlv = TlvUtils.getNextTlv(stream, allowFfStartingTag);
                list.add(tlv);
            }
        } catch (IOException e) {
            throw new AssertionError("Cannot happen");
        }

        return list;
    }


    @SuppressWarnings("SpellCheckingInspection")
    public static List<Tlv> getChildTlvs(final byte[] data, final EmvTag parentTag) throws TlvException {
        final List<Tlv> ret = new ArrayList<>();

        final List<Tlv> parents = getTlvs(data, parentTag);
        for (final Tlv parent : parents) {
            if (!ByteUtils.isByteArrayZeros(parent.getValueBytes())) {
                try (ByteArrayInputStream stream = new ByteArrayInputStream(parent.getValueBytes())) {
                    while (stream.available() > 0) {
                        final Tlv tlv = TlvUtils.getNextTlv(stream);
                        ret.add(tlv);
                    }
                } catch (IOException e) {
                    throw new AssertionError("Cannot happen");
                }
            }
        }

        return ret;
    }


    public static Tlv findInList(final List<Tlv> tlvs, final EmvTag tag) {
        Tlv ret = null;
        for (final Tlv tmp : tlvs) {
            if (tmp.getTag() == tag) {
                ret = tmp;
                break;
            }
        }

        return ret;
    }


    /**
     * Calculates how many bytes are needed to encode length value according to BER-TLV
     *
     * @param aLength
     * @return
     */
    public static int calculateBytesCountForLength(int aLength) {
        int ret;
        if (aLength < 0x80) {
            ret = 1;
        } else if (aLength < 0x100) {
            ret = 2;
        } else if (aLength < 0x10000) {
            ret = 3;
        } else if (aLength < 0x1000000) {
            ret = 4;
        } else {
            throw new IllegalStateException("length [" + aLength + "] out of range (0x1000000)");
        }
        return ret;
    }


    /**
     * Encodes length in bytes according to BER-TLV
     */
    public static byte[] encodeTagLength(int length) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (length < 0x80) {
            out.write((byte) length);

        } else if (length < 0x100) {
            out.write((byte) 0x81);
            out.write((byte) length);

        } else if (length < 0x10000) {
            out.write((byte) 0x82);
            out.write((byte) (length / 0x100));
            out.write((byte) (length % 0x100));

        } else if (length < 0x1000000) {
            out.write((byte) 0x83);
            out.write((byte) (length / 0x10000));
            out.write((byte) (length / 0x100));
            out.write((byte) (length % 0x100));
        } else {
            throw new IllegalArgumentException("length [" + length + "] out of range (0x1000000)");
        }

        return out.toByteArray();
    }
}