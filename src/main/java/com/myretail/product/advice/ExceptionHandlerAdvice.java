package com.myretail.product.advice;

import com.myretail.product.exception.ProductNotFoundException;
import com.myretail.product.exception.ProductPriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {ProductNotFoundException.class})
    protected ResponseEntity<String> productNotFoundException(ProductNotFoundException pnfEx) {
        return new ResponseEntity<String>(pnfEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ProductPriceNotFoundException.class})
    protected ResponseEntity<String> productPriceNotFoundException(ProductPriceNotFoundException ppnfEx) {
        return new ResponseEntity<String>(ppnfEx.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<String> applicationException(Exception ex) {
        return new ResponseEntity<String>("A Technical error has occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
