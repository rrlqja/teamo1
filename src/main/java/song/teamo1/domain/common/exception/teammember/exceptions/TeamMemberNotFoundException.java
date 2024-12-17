package song.teamo1.domain.common.exception.teammember.exceptions;

import song.teamo1.domain.common.exception.teammember.TeamMemberException;

public class TeamMemberNotFoundException extends TeamMemberException {
    public TeamMemberNotFoundException() {
        super("Team Member Not Found Exception");
    }

    public TeamMemberNotFoundException(String message) {
        super(message);
    }
}
