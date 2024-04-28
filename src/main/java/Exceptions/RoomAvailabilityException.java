package Exceptions;

public class RoomAvailabilityException extends RuntimeException {

    public RoomAvailabilityException(String message) {
        super(message);
    }
}