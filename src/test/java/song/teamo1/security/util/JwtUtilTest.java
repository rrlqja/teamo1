package song.teamo1.security.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class JwtUtilTest {

    @Test
    void generateJwt() {
        String test1 = JwtUtil.generateToken(1L, "test1", List.of("user", "admin"));
        log.info("jwt = {}", test1);
    }

    @Test
    void validateJwt() {
        String jwt = JwtUtil.generateToken(1L, "test1", List.of("user", "admin"));
        assertThat(JwtUtil.validateToken(jwt))
                .isTrue();
    }

}