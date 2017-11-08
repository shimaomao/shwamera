package ru.mera.sergeynazin.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
public class MyExceptionHandlers {

    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandlers.class);

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleSQLException(HttpServletRequest request, NotFoundException ex){
        logger.info("NotFoundException Occurred:: URL="
            +request.getRequestURL()
            +"\n"
            +ex.getLocalizedMessage());
        return ResponseEntity.badRequest().body(request);
    }

    @ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Exception occurred")
    @ExceptionHandler(Exception.class)
    public void handleIOException(Exception ex) {
        ResponseEntity response = new ResponseEntity(HttpStatus.BAD_REQUEST);
        try {
            throw ex;
        } catch (NotFoundException e) {
            // Caught specific Api Exception (can create Exception hierarchy later)
            // Can safely use its error message and status
            response.setStatus(((ApiException) e).getStatus());
            response.setMessage(e.getMessage());
        } catch (DomainMailServerUnavailableException e) {
            // Caught domain-specific exception which says that mail server is down
            response.setStatus(502);
            response.setMessage("Mail server is unavailable. Retry later.");
        } catch (DomainAccessDeniedException e) {
            // Caught another domain-specific exception
            response.setStatus(503);
            response.setMessage("You have insufficient privileges to perform this action.");
        } catch (Throwable unhandledError) {
            // Catch-all case
            logger.error("Unhandled error!", unhandledError); // Log full stack trace
            systemAlarm.unhandledError(unhandledError.getMessage()); // Notify admins
            response.setStatus(500);
            response.setMessage("Sorry, something is broken. We'll look into that.");
        }
        //returning 404 error code
    }
}