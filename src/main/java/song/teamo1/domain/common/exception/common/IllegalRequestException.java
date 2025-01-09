package song.teamo1.domain.common.exception.common;

import song.teamo1.domain.common.exception.TeamoException;

public class IllegalRequestException extends TeamoException {
    public IllegalRequestException() {
        super("Illegal Request Exception");
    }

    public IllegalRequestException(String message) {
        super(message);
    }
}
