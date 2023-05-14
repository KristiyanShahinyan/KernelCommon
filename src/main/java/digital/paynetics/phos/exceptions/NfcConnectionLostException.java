package digital.paynetics.phos.exceptions;

public class NfcConnectionLostException extends RuntimeException {
    public NfcConnectionLostException() {
    }


    public NfcConnectionLostException(String s) {
        super(s);
    }


    public NfcConnectionLostException(String s, Throwable throwable) {
        super(s, throwable);
    }


    public NfcConnectionLostException(Throwable throwable) {
        super(throwable);
    }
}
