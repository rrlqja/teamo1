package song.teamo1.domain.common.exception.team.exceptions;

import song.teamo1.domain.common.exception.team.TeamException;

public class DuplicateTeamNameException extends TeamException {
    public DuplicateTeamNameException() {
        super("Duplicate team name Exception");
    }

    public DuplicateTeamNameException(String message) {
        super(message);
    }
}
