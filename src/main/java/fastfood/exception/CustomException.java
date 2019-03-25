package fastfood.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final String code;
  private final String message;

  private final HttpStatus httpStatus;

  public CustomException(String code, String message, HttpStatus httpStatus) {
    this.code = code;
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }
}