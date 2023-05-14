package digital.paynetics.phos.exceptions;


/**
 * Thrown when data is in missing or wrong according to EMV standards
 */
public class EmvException extends Exception {
    public EmvException() {
    }


    public EmvException(String message) {
        super(message);
    }


    public EmvException(String message, Throwable cause) {
        super(message, cause);
    }


    public EmvException(Throwable cause) {
        super(cause);
    }
}
