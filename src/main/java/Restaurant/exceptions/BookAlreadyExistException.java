package Restaurant.exceptions;

public class BookAlreadyExistException extends Exception {

    public BookAlreadyExistException(String message) {
        super(message);
    }

    public BookAlreadyExistException() {
        super("El libro ya existe");
    }
}
