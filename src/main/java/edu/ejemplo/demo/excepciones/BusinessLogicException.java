package edu.ejemplo.demo.excepciones;

/**
 * Created by admin on 7/5/2016.
 */
public class BusinessLogicException extends RuntimeException {

    public BusinessLogicException(String exMessage){
        super(exMessage);
    }
}
