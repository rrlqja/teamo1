package song.teamo1.domain.common.exception;

public class TeamoException extends RuntimeException {
    public TeamoException() {
        super("Teamo Exception");
    }

    public TeamoException(String message) {
        super(message);
    }
}
