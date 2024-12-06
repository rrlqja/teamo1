package song.teamo1.domain.team.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("Team Not Found Exception");
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}
