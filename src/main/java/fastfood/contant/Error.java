package fastfood.contant;

public enum  Error {
    TOKEN_INVALID("C0001", "Token is expired or invalid"),
    FORMAT_INVALID("C0002", "Format is invalid"),
    DATA_NOT_FOUND("C0003", "Data not found"),
    INTERNAL_ERROR("C0004", "Internal error"),
    REQUIRED_FIELD("C0005","Required"),
    CONSTRAINT_VIOLATION("C0006", "Constraint violation"),
    METHOD_NOT_SUPPORTED("C0007", "method not supported"),
    MEDIA_TYPE_NOT_SUPPORTED("C0008", "Media type not supported"),
    BAD_CREDENTIALS("C0009", "Bad credentials"),
    FORBIDDEN("C0010", "Access denied"),
    PASSWORD_NOT_MATCH("B0001", "Password not match"),
    USER_EXIST("B0002", "Username has already existed");
    private String code;
    private String message;

    Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
