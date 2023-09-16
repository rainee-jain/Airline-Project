package com.assignment.smartaviation.advice;

import com.assignment.smartaviation.exception.AirlineServiceException;
import com.assignment.smartaviation.model.ErrorMessage;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Application exception handler.
 * This is a Global Exception Handler.
 */
@ControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    /**
     * Handle country api exception error message.
     *
     * @param ex the ex
     * @return the error message
     */
    @ExceptionHandler(AirlineServiceException.class)
    public ResponseEntity<Object>  handleCountryApiException(AirlineServiceException ex, HttpServletRequest request){
        Logger.logMsg(4,"Reported Exception : "+ex.getMessage());
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrorCode(ex.getErrorCode());
        errorMessage.setErrorMessage(ex.getErrorMessage());
        errorMessage.setStatusCode(ex.getStatus());
        return new ResponseEntity<>(errorMessage, HttpStatus.valueOf(ex.getStatus()));

    }
}
