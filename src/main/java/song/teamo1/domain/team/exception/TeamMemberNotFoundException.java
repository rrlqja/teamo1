package song.teamo1.domain.team.exception;

public class TeamMemberNotFoundException extends RuntimeException {
    public TeamMemberNotFoundException() {
        super("Team Member Not Found Exception");
    }

    public TeamMemberNotFoundException(String message) {
        super(message);
    }
}
