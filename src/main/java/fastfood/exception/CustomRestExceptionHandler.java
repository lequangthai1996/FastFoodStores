package fastfood.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import fastfood.contant.Error;
import fastfood.domain.ErrorCustom;
import fastfood.domain.ResponseCommonAPI;
import javassist.NotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        List<ErrorCustom> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(
                    new ErrorCustom(error.getField(), Error.REQUIRED_FIELD.getCode(), error.getDefaultMessage()));
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(new ErrorCustom(error.getObjectName(), Error.REQUIRED_FIELD.getCode(),
                    error.getDefaultMessage()));
        }
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(false);
        res.setError(errors);
        return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorCustom> errors = new ArrayList<>();
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        errors.add(
                new ErrorCustom(ex.getParameterName(), Error.REQUIRED_FIELD.getCode(), " parameter is missing"));
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(false);
        res.setError(errors);
        return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        List<ErrorCustom> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(
                    new ErrorCustom(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath(),
                            Error.CONSTRAINT_VIOLATION.getCode(), violation.getMessage()));
        }
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(false);
        res.setError(errors);
        return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        List<ErrorCustom> errors = new ArrayList<>();
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        errors.add(new ErrorCustom(ex.getName(), Error.FORMAT_INVALID.getCode(),
                " should be of type " + ex.getRequiredType().getName()));
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(false);
        res.setError(errors);
        return new ResponseEntity<>(res, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        JsonMappingException jme = (JsonMappingException) ex.getCause();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseCommonAPI.builder().success(false)
                        .error(Collections
                                .singletonList(
                                        new ErrorCustom(jme.getPath().get(0).getFieldName(),
                                                Error.FORMAT_INVALID
                                                        .getCode(),
                                                jme.getPath().get(0).getFieldName() + " invalid format")))
                        .build());
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(false);
        res.setError(Collections.singletonList(new ErrorCustom("", "", error)));
        return new ResponseEntity<Object>(res, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(false);
        res.setError(Collections
                .singletonList(new ErrorCustom("", Error.METHOD_NOT_SUPPORTED.getCode(), builder.toString())));
        return new ResponseEntity<Object>(res, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers, HttpStatus status, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));
        ResponseCommonAPI res = new ResponseCommonAPI();
        res.setSuccess(false);
        res.setError(Collections.singletonList(new ErrorCustom("", Error.MEDIA_TYPE_NOT_SUPPORTED.getCode(),
                builder.substring(0, builder.length() - 2))));
        return new ResponseEntity<Object>(res, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @ExceptionHandler({ NoResultException.class, NotFoundException.class, EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleNotEntityFound(Exception e, WebRequest request) {
        logger.error(e.getMessage(), e.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ResponseCommonAPI.builder()
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new ErrorCustom(null, Error.DATA_NOT_FOUND.getCode(), e.getMessage())))
                        .build());
    }

    @ExceptionHandler({ CustomException.class})
    public ResponseEntity<Object> handleCustomException(Exception e, WebRequest request) {
        logger.error(e.getMessage(), e.fillInStackTrace());
        CustomException customException = (CustomException) e;
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ResponseCommonAPI.builder()
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new ErrorCustom("", customException.getCode(), customException.getMessage())))
                        .build());
    }



    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ResponseCommonAPI.builder().success(false)
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new ErrorCustom("", Error.FORBIDDEN.getCode(), ex.getMessage())))
                        .build());
    }

    @ExceptionHandler({ NotAllowBusiness.class })
    public ResponseEntity<Object> handleNotAllowBusiness(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        NotAllowBusiness notAllowBusiness = (NotAllowBusiness) ex;
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ResponseCommonAPI.builder().success(false)
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new ErrorCustom("", notAllowBusiness.getCode(), notAllowBusiness.getMessage())))
                        .build());
    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        logger.error(ex.getMessage(), ex.fillInStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ResponseCommonAPI.builder().success(false)
                        .success(false)
                        .error(Collections
                                .singletonList(
                                        new ErrorCustom("", Error.INTERNAL_ERROR.getCode(), ex.getMessage())))
                        .build());
    }
}