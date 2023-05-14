package digital.paynetics.phos.exceptions;

public class InvalidDynamicApplicationData extends Exception {
    public InvalidDynamicApplicationData() {
    }


    public InvalidDynamicApplicationData(String message) {
        super(message);
    }


    public InvalidDynamicApplicationData(String message, Throwable cause) {
        super(message, cause);
    }


    public InvalidDynamicApplicationData(Throwable cause) {
        super(cause);
    }
}
