package song.teamo1.domain.common.exception.team;

import song.teamo1.domain.common.exception.TeamoException;

public class TeamException extends TeamoException {
    public TeamException() {
        super("Team Exception");
    }

    public TeamException(String message) {
        super(message);
    }
}