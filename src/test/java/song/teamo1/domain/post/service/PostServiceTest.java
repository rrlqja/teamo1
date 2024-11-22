package song.teamo1.domain.post.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.post.SavePostDto;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class PostServiceTest {
    @Autowired
    PostService postService;

    @Test
    void successSaveRecruitment() {
        SavePostDto savePostDto = new SavePostDto("testTitle", "testContent");

        assertDoesNotThrow(() -> postService.saveRecruitmentPost(savePostDto, null));
    }
}