package digital.paynetics.phos.kernel.common.emv.cert;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CaPublicKeyDbJson {
    @SerializedName("rid")
    public String rid;
    @SerializedName("keys")
    public List<CaPublicKeyData> keys;


    public CaPublicKeyDbJson(String rid, List<CaPublicKeyData> keys) {
        this.rid = rid;
        this.keys = keys;
    }
}
