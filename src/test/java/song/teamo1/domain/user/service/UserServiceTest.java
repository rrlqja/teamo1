package song.teamo1.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.user.dto.SaveUserDto;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void successSaveUser() {
        SaveUserDto saveUserDto = new SaveUserDto("testUsername", "testPassword", "testName");

        assertDoesNotThrow(() -> userService.saveUser(saveUserDto));
    }
}