package pl.application.exceptions;

public abstract class ReservationCustomException extends RuntimeException {
    abstract public int getCode();

    public ReservationCustomException(String message) {
        super(message);
    }
}
