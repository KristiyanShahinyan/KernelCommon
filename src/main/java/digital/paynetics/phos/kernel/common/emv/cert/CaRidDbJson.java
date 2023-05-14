package digital.paynetics.phos.kernel.common.emv.cert;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class CaRidDbJson {
    @SerializedName("list")
    public List<CaPublicKeyDbJson> list;


    public CaRidDbJson(List<CaPublicKeyDbJson> list) {
        this.list = list;
    }
}
