package com.viettel.arpu.exception;

import com.viettel.arpu.constant.AppConstants;
import com.viettel.arpu.constant.MessageCode;
import com.viettel.arpu.locale.Translator;
import com.viettel.arpu.model.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ControllerAdvice
@Slf4j
public class ExceptionTranslator extends ResponseEntityExceptionHandler {

    //Common error handling

    @Autowired
    @Qualifier("validatorMessageSource")
    private MessageSource validatorMessageSource;

    @ExceptionHandler(CustomerHasBeenLockException.class)
    public final ResponseEntity<Object> handleCustomerHasBeenLockException(CustomerHasBeenLockException ex) {
        return badRequest(ex.toErrorResponse());
    }

    @ExceptionHandler(CustomerHasBeenUnLockException.class)
    public final ResponseEntity<Object> handleCustomerHasBeenUnLockException(CustomerHasBeenUnLockException ex) {
        return badRequest(ex.toErrorResponse());
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public final ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException ex) {
        return notFound(ex.toErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex) {
        log.error(ex.getMessage(), ex);
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(AppConstants.INTERNAL_SERVER_ERROR.getCode(),
                AppConstants.INTERNAL_SERVER_ERROR.getMessage(),
                details);
        return ResponseEntity.status(HttpStatus.OK).body(error);
    }

    @ExceptionHandler(FileInvalidException.class)
    public final ResponseEntity<Object> handleFileInvalidException(FileInvalidException ex) {
        return badRequest(ex.toErrorResponse());
    }

    @ExceptionHandler(CustomerVersionInvalidException.class)
    public final ResponseEntity<Object> handleCustomerVersionInvalidException(CustomerVersionInvalidException ex) {
        return badRequest(ex.toErrorResponse());
    }


    @ExceptionHandler(LoanNotFoundException.class)
    public final ResponseEntity<Object> handleLoanNotFoundException(LoanNotFoundException ex) {
        return notFound(ex.toErrorResponse());
    }

    @ExceptionHandler(ReasonMissingException.class)
    public final ResponseEntity<Object> handleReasonMissingException(ReasonMissingException ex) {
        return notFound(ex.toErrorResponse());
    }

    @ExceptionHandler(LoanStatusInvalidException.class)
    public final ResponseEntity<Object> handleLoanStatusInvalidException(LoanStatusInvalidException ex) {
        return notFound(ex.toErrorResponse());
    }

    @ExceptionHandler(CodeInvalidException.class)
    public final ResponseEntity<Object> handleCodeInvalidException(CodeInvalidException ex) {
        return notFound(ex.toErrorResponse());
    }

    @ExceptionHandler(LoanHasBeenApprovedException.class)
    public final ResponseEntity<Object> loanHasBeenApprovedFoundException(LoanHasBeenApprovedException ex) {
        return notFound(ex.toErrorResponse());
    }

    @ExceptionHandler(IdFormatException.class)
    public final ResponseEntity<Object> idInvalidException(IdFormatException ex) {
        return notFound(ex.toErrorResponse());
    }

    @ExceptionHandler({SocketTimeoutException.class, ResourceAccessException.class})
    public final ResponseEntity<Object> handleSocketTimeoutException(Exception ex) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ErrorResponse(AppConstants.CONNECTION_TIMEOUT));
    }

    @ExceptionHandler(MbNotFoundException.class)
    public final ResponseEntity<Object> handleMbNotFoundException(MbNotFoundException ex) {
        return notFound(new ErrorResponse(AppConstants.BAD_REQUEST, Collections.singletonList(ex.getMessage())));
    }

    @ExceptionHandler(MbResponseException.class)
    public final ResponseEntity<Object> handleMbResponseException(MbResponseException ex) {
        return notFound(new ErrorResponse("ARPU452", ex.getMessage()));
    }

    //    @ExceptionHandler(SocketTimeoutException)

    //--------- Binding and validating handling

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Object> handleLoanNotFoundException(MethodArgumentTypeMismatchException ex) {
        ErrorResponse error = new ErrorResponse(AppConstants.BAD_REQUEST);
        return badRequest(error);
    }

//    @ExceptionHandler(IllegalArgumentException.class)
//    public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
//        ErrorResponse error = new ErrorResponse(AppConstants.BAD_REQUEST, Collections.singletonList(Translator.toLocale("error.msg.no.enum")));
//        return badRequest(error);
//    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(AppConstants.BAD_REQUEST);
        return badRequest(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                        HttpHeaders headers,
                                                        HttpStatus status,
                                                        WebRequest request) {
        ErrorResponse error = new ErrorResponse(AppConstants.TYPE_MISMATCH);
        error.getDetails().add(validatorMessageSource.getMessage(error.getCode()
                , null
                , LocaleContextHolder.getLocale()));
        return badRequest(error);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getErrorMessage(ex.getBindingResult(), ex);
    }

    private ResponseEntity<Object> getErrorMessage(BindingResult bindingResult, Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(AppConstants.BAD_REQUEST);


        for (ObjectError error : bindingResult.getAllErrors()) {
            Optional.ofNullable(error.getCodes()).ifPresent(codes -> {
                if (codes.length > 0) {
                    String errorCode = codes[0];
                    errorResponse.getDetails().add(validatorMessageSource.getMessage(errorCode, null, LocaleContextHolder.getLocale()));
                }
            });
        }


        return badRequest(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        return getErrorMessage(ex.getBindingResult(), ex);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new ErrorResponse(AppConstants.NOT_FOUND));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return ResponseEntity.ok(new ErrorResponse(AppConstants.BAD_REQUEST, Collections.singletonList(Translator.toLocale("error.mess.convert"))));
    }

    private ResponseEntity<Object> badRequest(Object body) {
        return ResponseEntity.ok().body(body);
    }

    private ResponseEntity<Object> notFound(Object body) {
        return ResponseEntity.ok().body(body);
    }
}
