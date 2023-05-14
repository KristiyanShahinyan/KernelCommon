package digital.paynetics.phos.kernel.common.emv.tag;

import java.util.Arrays;


public class TagAndLength {
    private EmvTag tag;
    private int length;


    public TagAndLength(final EmvTag tag, final int length) {
        this.tag = tag;
        this.length = length;
    }


    public EmvTag getTag() {
        return tag;
    }


    public int getLength() {
        return length;
    }


    public byte[] getBytes() {
        final byte[] tagBytes = tag.getTagBytes();
        final byte[] tagAndLengthBytes = Arrays.copyOf(tagBytes, tagBytes.length + 1);
        tagAndLengthBytes[tagAndLengthBytes.length - 1] = (byte) length;
        return tagAndLengthBytes;
    }


    @Override
    public String toString() {
        return tag.toString() + " length: " + length;
    }

}
