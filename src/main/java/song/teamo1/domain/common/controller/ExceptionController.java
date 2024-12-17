package song.teamo1.domain.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import song.teamo1.domain.common.dto.ExceptionDto;
import song.teamo1.domain.common.exception.TeamoException;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {TeamoException.class})
    public ResponseEntity<ExceptionDto> teamMemberNotFound(TeamoException e) {
        return ResponseEntity.badRequest().body(new ExceptionDto(e.getMessage()));
    }
}
