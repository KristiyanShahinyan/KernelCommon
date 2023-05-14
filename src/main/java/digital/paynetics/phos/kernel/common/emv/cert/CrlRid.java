package digital.paynetics.phos.kernel.common.emv.cert;

public interface CrlRid {
    boolean isPresent(int caPublicKeyIndex, byte[] serial);
}
