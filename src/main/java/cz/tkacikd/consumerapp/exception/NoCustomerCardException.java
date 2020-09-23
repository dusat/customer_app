package cz.tkacikd.consumerapp.exception;

public class NoCustomerCardException extends RuntimeException {

    public NoCustomerCardException(String message){
        super(message);
    }
}
