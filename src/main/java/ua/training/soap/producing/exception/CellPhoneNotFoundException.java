package ua.training.soap.producing.exception;

public class CellPhoneNotFoundException extends RuntimeException{

    public CellPhoneNotFoundException(String message) {
        super(message);
    }
}
