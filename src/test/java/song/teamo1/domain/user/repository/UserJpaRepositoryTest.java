package song.teamo1.domain.user.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.user.entity.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class UserJpaRepositoryTest {
    @Autowired
    UserJpaRepository userRepository;

    @Test
    void findUserByUsername() {
        Optional<User> user = userRepository.findByUsername("1");
    }

}