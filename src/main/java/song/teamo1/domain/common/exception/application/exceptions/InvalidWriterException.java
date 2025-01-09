package song.teamo1.domain.common.exception.application.exceptions;

import song.teamo1.domain.common.exception.application.ApplicationException;

public class InvalidWriterException extends ApplicationException {
    public InvalidWriterException() {
        super("Invalid Writer Exception");
    }

    public InvalidWriterException(String message) {
        super(message);
    }
}
