package fastfood.exception;

public class NotAllowBusiness extends Exception {
    private static final long serialVersionUID = 1L;

    private final String message;
    private final String code;

    public NotAllowBusiness(String message, String code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}