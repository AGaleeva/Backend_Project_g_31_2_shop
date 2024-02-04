package de.aittr.g_31_2_shop.exception_handling;

import de.aittr.g_31_2_shop.exception_handling.meaningful_exceptions.*;
import de.aittr.g_31_2_shop.exception_handling.meaningless_exceptions.FourthTestException;
import de.aittr.g_31_2_shop.exception_handling.meaningless_exceptions.ThirdTestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonAdvice {

    private Logger logger = LoggerFactory.getLogger(CommonAdvice.class);

    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleException(ThirdTestException e) {
        logger.error(String.format("Error: %s", e.getMessage()));
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FourthTestException.class)
    public ResponseEntity<Response> handleException(FourthTestException e) {
        logger.error(String.format("Error: %s", e.getMessage()));
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNameNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNameNotFoundException e) {
        return new ResponseEntity<>(new Response(e.getMessage(), e.getTimestamp()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongIdException.class)
    public ResponseEntity<Response> handleException(WrongIdException e) {
        return new ResponseEntity<>(new Response(e.getMessage(), e.getTimestamp()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NegativeProductPriceException.class)
    public ResponseEntity<Response> handleException(NegativeProductPriceException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ZeroProductPriceException.class)
    public ResponseEntity<Response> handleException(ZeroProductPriceException e) {
        return new ResponseEntity<>(new Response(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
