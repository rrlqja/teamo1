package song.teamo1.domain.common.exception.team.exceptions;

import song.teamo1.domain.common.exception.TeamoException;

public class TeamNotFoundException extends TeamoException {
    public TeamNotFoundException() {
        super("Team Not Found Exception");
    }

    public TeamNotFoundException(String message) {
        super(message);
    }
}
