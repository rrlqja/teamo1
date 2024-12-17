package song.teamo1.domain.common.exception.teammember;

import song.teamo1.domain.common.exception.TeamoException;

public class TeamMemberException extends TeamoException {
    public TeamMemberException() {
        super("Team Member Exception");
    }

    public TeamMemberException(String message) {
        super(message);
    }
}
