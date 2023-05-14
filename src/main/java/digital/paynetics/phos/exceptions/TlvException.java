package digital.paynetics.phos.exceptions;

/**
 * Exception during TLV reading
 */
public class TlvException extends Exception {

    /**
     * Constructor using field
     *
     * @param pCause cause
     */
    public TlvException(final String pCause) {
        super(pCause);
    }


    public TlvException(Throwable cause) {
        super(cause);
    }


    public TlvException(String message, Throwable cause) {
        super(message, cause);
    }
}
