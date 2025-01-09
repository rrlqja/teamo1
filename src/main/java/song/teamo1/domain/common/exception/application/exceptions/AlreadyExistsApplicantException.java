package song.teamo1.domain.common.exception.application.exceptions;

import song.teamo1.domain.common.exception.application.ApplicationException;

public class AlreadyExistsApplicantException extends ApplicationException {
    public AlreadyExistsApplicantException() {
        super("Already Exists Application Exception");
    }

    public AlreadyExistsApplicantException(String message) {
        super(message);
    }
}
