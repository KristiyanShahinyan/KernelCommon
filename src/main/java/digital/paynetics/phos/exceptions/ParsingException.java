package digital.paynetics.phos.exceptions;

public class ParsingException extends EmvException {
    public ParsingException() {
    }


    public ParsingException(String message) {
        super(message);
    }


    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }


    public ParsingException(Throwable cause) {
        super(cause);
    }
}
