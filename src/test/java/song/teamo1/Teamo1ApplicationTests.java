package song.teamo1;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
		"JASYPT_ENCRYPTOR_PASSWORD=song"
})
class Teamo1ApplicationTests {

	@Test
	void contextLoads() {
	}

}
