package digital.paynetics.phos.kernel.common.emv.cert;

public class CrlItemJson {
    public String rid;
    public String target;


    public CrlItemJson(String rid, String target) {
        this.rid = rid;
        this.target = target;
    }
}
