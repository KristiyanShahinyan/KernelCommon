package digital.paynetics.phos.kernel.common.emv.cert;

import com.google.gson.annotations.SerializedName;

import digital.paynetics.phos.kernel.common.crypto.EmvPublicKey;


public final class CaPublicKeyData {
    @SerializedName("rid")
    private final String rid;
    @SerializedName("index")
    private final int index;
    @SerializedName("public_key")
    private final EmvPublicKey publicKey;
    @SerializedName("hash")
    private final byte[] hash;
    @SerializedName("key_type")
    private final KeyType keyType;


    public CaPublicKeyData(String rid, int index, EmvPublicKey publicKey, byte[] hash, KeyType keyType) {
        this.rid = rid;
        this.index = index;
        this.publicKey = publicKey;
        this.hash = hash;
        this.keyType = keyType;
    }


    public String getRid() {
        return rid;
    }


    public int getIndex() {
        return index;
    }


    public EmvPublicKey getPublicKey() {
        return publicKey;
    }


    public byte[] getHash() {
        return hash;
    }


    public KeyType getKeyType() {
        return keyType;
    }


    public enum KeyType {
        LIVE,
        TEST
    }
}
