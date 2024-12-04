package song.teamo1.domain.team.exception;

public class DuplicateTeamNameException extends RuntimeException {
    public DuplicateTeamNameException() {
        super("Duplicate team name Exception");
    }

    public DuplicateTeamNameException(String message) {
        super(message);
    }
}
