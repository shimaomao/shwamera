package ru.mera.sergeynazin.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Priority(Integer.MIN_VALUE)
@RestControllerAdvice(annotations = RestController.class)
public class MyExceptionHandlers extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandlers.class);

    private ResponseEntity<?> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(HttpServletRequest request, NotFoundException ex) {
        logger.info("NotFoundException Occurred:: URL="
            +request.getRequestURL()
            +"\n"
            +ex.getLocalizedMessage());

        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }



    @ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Exception occurred")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleIOException(Exception ex, HttpServletResponse x) {
        try {
            throw ex;
        } catch (NotFoundException e) {
            // Caught specific Api Exception (can create Exception hierarchy later)
            // Can safely use its error message and status
            ApiError response = new ApiError(HttpStatus.NOT_FOUND);
            response.setStatus(e.getStatus());
            response.setMessage(e.getMessage());
        } catch (DomainMailServerUnavailableException e) {
            // Caught domain-specific exception which says that mail server is down
            ApiError response = new ApiError(HttpStatus.BAD_GATEWAY);
            response.setMessage("Mail server is unavailable. Retry later.");
        } catch (DomainAccessDeniedException e) {
            // Caught another domain-specific exception
            ApiError response = new ApiError(HttpStatus.SERVICE_UNAVAILABLE);
            response.setMessage("You have insufficient privileges to perform this action.");
        } catch (Throwable unhandledError) {
            // Catch-all case
            logger.error("Unhandled error!", unhandledError); // Log full stack trace
            systemAlarm.unhandledError(unhandledError.getMessage()); // Notify admins
            ApiError response = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Sorry, something is broken. We'll look into that.");
        }
        return ResponseEntity.notFound().build();
        //returning 404 error code
    }
}