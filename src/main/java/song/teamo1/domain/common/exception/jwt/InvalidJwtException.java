package song.teamo1.domain.common.exception.jwt;

import song.teamo1.domain.common.exception.TeamoException;

public class InvalidJwtException extends TeamoException {
    public InvalidJwtException() {
        super("Invalid JWT Exception");
    }

    public InvalidJwtException(String message) {
        super(message);
    }
}
