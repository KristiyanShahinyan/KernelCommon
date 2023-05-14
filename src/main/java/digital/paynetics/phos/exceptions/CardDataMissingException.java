package digital.paynetics.phos.exceptions;

public class CardDataMissingException extends EmvException {
    public CardDataMissingException() {
    }


    public CardDataMissingException(String message) {
        super(message);
    }


    public CardDataMissingException(String message, Throwable cause) {
        super(message, cause);
    }


    public CardDataMissingException(Throwable cause) {
        super(cause);
    }
}
