package digital.paynetics.phos.kernel.common.emv.kernel.common;


public final class CvmResults {
    private final boolean isCvmNotPerformed;

    private final byte cvmCodeRaw;
    private final CvmListRule.CvmCode method;
    private final CvmListRule.ConditionCode conditionCode;
    private final Result result;


    private CvmResults(boolean isCvmNotPerformed,
                       byte cvmCodeRaw, CvmListRule.CvmCode method,
                       CvmListRule.ConditionCode conditionCode,
                       Result result) {
        this.cvmCodeRaw = cvmCodeRaw;
        if (isCvmNotPerformed) {
            if (method != null || conditionCode != null) {
                throw new IllegalArgumentException("When isCvmNotPerformed method and conditionCode parameters mus be " +
                        "null");
            }
        } else {
            if (method == null || conditionCode == null) {
                throw new IllegalArgumentException("When isCvmNotPerformed method and conditionCode parameters must NOT " +
                        "be null");
            }
        }

        this.isCvmNotPerformed = isCvmNotPerformed;
        this.method = method;
        this.conditionCode = conditionCode;
        this.result = result;
    }


    public static CvmResults createCvmNotPerformed(boolean isFailed) {
        return new CvmResults(true, (byte) 0, null, null, isFailed ? Result.FAILED : Result.UNKNOWN);
    }


    public static CvmResults createCvmFailed() {
        return new CvmResults(true, (byte) 0, null, null, Result.FAILED);
    }


    public static CvmResults createCvmPerformed(byte cvmCodeRaw,
                                                CvmListRule.CvmCode code,
                                                CvmListRule.ConditionCode conditionCode,
                                                Result result) {

        return new CvmResults(false, cvmCodeRaw, code, conditionCode, result);
    }


    public byte[] getBytes() {
        return new byte[]{isCvmNotPerformed ? 0x3f : cvmCodeRaw,
                (byte) (isCvmNotPerformed ? 0 : conditionCode.getCode()),
                (byte) result.getCode()};
    }


    public enum Result {
        UNKNOWN(0),
        FAILED(1),
        SUCCESSFUL(2);


        private final int code;


        Result(int code) {
            this.code = code;
        }


        public int getCode() {
            return code;
        }
    }


    public boolean isCvmNotPerformed() {
        return isCvmNotPerformed;
    }


    public CvmListRule.CvmCode getMethod() {
        return method;
    }


    public CvmListRule.ConditionCode getConditionCode() {
        return conditionCode;
    }


    public Result getResult() {
        return result;
    }
}
