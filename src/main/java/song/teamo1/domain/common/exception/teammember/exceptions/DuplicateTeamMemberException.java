package song.teamo1.domain.common.exception.teammember.exceptions;

import song.teamo1.domain.common.exception.teammember.TeamMemberException;

public class DuplicateTeamMemberException extends TeamMemberException {
    public DuplicateTeamMemberException() {
        super("Duplicate TeamMember Exception");
    }

    public DuplicateTeamMemberException(String message) {
        super(message);
    }
}
