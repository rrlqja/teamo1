package song.teamo1.domain.common.exception.team.exceptions;

import song.teamo1.domain.common.exception.team.TeamException;

public class EditNotAllowedException extends TeamException {
    public EditNotAllowedException() {
        super("Edit Not Allowed Exception");
    }

    public EditNotAllowedException(String message) {
        super(message);
    }
}
