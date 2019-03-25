package fastfood.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@ControllerAdvice
@RestController
public class CustomRestExceptionHandler<ControllerAdvice> extends ResponseEntityExceptionHandler {

    /**
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     * @desctiption Handle method argument not valid
     * @author at-vudang
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        List<CustomError> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(
                    new CustomError(error.getField(), Error.REQUIRED_FIELD.getCode(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(new CustomError(error.getObjectName(), Error.REQUIRED_FIELD.getCode(),
                    error.getDefaultMessage()));
        }
        ResponseDataAPI res = new ResponseDataAPI();
        res.setSuccess(false);
        res.setError(errors);
        return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }

    /**
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     * @desctiption Handle missing servlet request parameter
     * @author at-vudang
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<CustomError> errors = new ArrayList<>();
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        errors.add(
                new CustomError(ex.getParameterName(), Error.REQUIRED_FIELD.getCode(), " parameter is missing"));
        ResponseDataAPI res = new ResponseDataAPI();
        res.setSuccess(false);
        res.setError(errors);
        return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex
     * @param request
     * @return
     * @desctiption Handle constraint violation
     * @author at-vudang
     */
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        List<CustomError> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(
                    new CustomError(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath(),
                            Error.CONSTRAINT_VIOLATION.getCode(), violation.getMessage()));
        }
        ResponseDataAPI res = new ResponseDataAPI();
        res.setSuccess(false);
        res.setError(errors);
        return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex
     * @param request
     * @return
     * @desctiption Handle method argument type mismatch
     * @author at-vudang
     */
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        List<CustomError> errors = new ArrayList<>();
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        errors.add(new CustomError(ex.getName(), Error.INVALID_VALUE_FORMAT.getCode(),
                " should be of type " + ex.getRequiredType().getName()));
        ResponseDataAPI res = new ResponseDataAPI();
        res.setSuccess(false);
        res.setError(errors);
        return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     * @description Handle http message not readable
     * @author at-vudang
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDataAPI.builder().success(false)
                        .error(Collections
                                .singletonList(
                                        new CustomError(jme.getPath().get(0).getFieldName(),
                                                Error.INVALID_VALUE_FORMAT
                                                        .getCode(),
                                                jme.getPath().get(0).getFieldName() + " invalid format")))
                        .build());
    }

    /**
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     * @desctiption Handle no handler found exception
     * @author at-vudang
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ResponseDataAPI res = new ResponseDataAPI();
        res.setSuccess(false);
        res.setError(Collections.singletonList(new CustomError("", "", error)));
        return new ResponseEntity<Object>(res, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    /**
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     * @desctiption Handle http request method not support
     * @author at-vudang
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        ResponseDataAPI res = new ResponseDataAPI();
        res.setSuccess(false);
        res.setError(Collections
                .singletonList(new CustomError("", Error.METHOD_NOT_SUPPORTED.getCode(), builder.toString())));
        return new ResponseEntity<Object>(res, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     * @desctiption Handle http media type not supported
     * @author at-vudang
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
        ResponseDataAPI res = new ResponseDataAPI();
        res.setSuccess(false);
        res.setError(Collections.singletonList(new CustomError("", Error.MEDIA_TYPE_NOT_SUPPORTED.getCode(),
                builder.substring(0, builder.length() - 2))));
        return new ResponseEntity<Object>(res, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * @param e
     * @param request
     * @return
     * @desctiption Handle not entity not found
     * @author at-vudang
     */
    @ExceptionHandler({ NoResultException.class, NotFoundException.class, EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleNotEntityFound(Exception e, WebRequest request) {
        logger.error(e.getMessage(), e.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseDataAPI.builder()
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new CustomError(null, Error.DATA_NOT_FOUND.getCode(), e.getMessage())))
                        .build());
    }

    /**
     * @param e
     * @param request
     * @return
     * @desctiption Handle not entity not found
     * @author at-vudang
     */
    @ExceptionHandler({ CustomException.class})
    public ResponseEntity<Object> handleCustomException(Exception e, WebRequest request) {
        logger.error(e.getMessage(), e.fillInStackTrace());
        CustomException customException = (CustomException) e;
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseDataAPI.builder()
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new CustomError("", customException.getCode(), customException.getMessage())))
                        .build());
    }


    /**
     * @param ex
     * @param request
     * @return
     * @desctiption Handle all
     * @author at-vudang
     */
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseDataAPI.builder().success(false)
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new CustomError("", Error.FORBIDDEN.getCode(), ex.getMessage())))
                        .build());
    }


    /**
     * @param ex
     * @param request
     * @return
     * @desctiption Handle not allow business
     * @author at-vudang
     */
    @ExceptionHandler({ NotAllowBusiness.class })
    public ResponseEntity<Object> handleNotAllowBusiness(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        NotAllowBusiness notAllowBusiness = (NotAllowBusiness) ex;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseDataAPI.builder().success(false)
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new CustomError("", notAllowBusiness.getCode(), notAllowBusiness.getMessage())))
                        .build());
    }

    /**
     * @param ex
     * @param request
     * @return
     * @desctiption Handle all
     * @author at-vudang
     */
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseDataAPI.builder().success(false)
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new CustomError("", Error.INTERNAL_ERROR.getCode(), ex.getMessage())))
                        .build());
    }
}