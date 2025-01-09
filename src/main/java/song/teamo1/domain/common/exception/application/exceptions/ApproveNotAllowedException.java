package song.teamo1.domain.common.exception.application.exceptions;

import song.teamo1.domain.common.exception.application.ApplicationException;

public class ApproveNotAllowedException extends ApplicationException {
    public ApproveNotAllowedException() {
        super("Approve Not Allowed Exception");
    }

    public ApproveNotAllowedException(String message) {
        super(message);
    }
}
