package digital.paynetics.phos.kernel.common.nfc;

/**
 * Thrown then Status word is not recognized, i.e. not in {@link ApduResponseStatusWord}
 */
class AdpuResponseException extends Exception {
    AdpuResponseException() {
    }


    AdpuResponseException(String message) {
        super(message);
    }


    AdpuResponseException(String message, Throwable cause) {
        super(message, cause);
    }


    AdpuResponseException(Throwable cause) {
        super(cause);
    }
}
