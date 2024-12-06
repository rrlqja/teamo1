package song.teamo1.domain.post.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.post.dto.SaveTeamingDto;
import song.teamo1.domain.post.entity.Teaming;
import song.teamo1.domain.post.repository.PostJpaRepository;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class PostServiceTest {
    @Autowired
    PostService postService;
    @Autowired
    PostJpaRepository postJpaRepository;
//    @Autowired
//    RecruitmentPostJpaRepository recruitmentPostJpaRepository;

//    @Test
//    void successSaveRecruitment() {
//        SavePostDto savePostDto = new SavePostDto("testTitle", "testContent");
//
//        assertDoesNotThrow(() -> postService.saveRecruitmentPost(savePostDto, null));
//    }

    @Test
    void t1() {
        SaveTeamingDto recruitmentPostDto = new SaveTeamingDto("test title", "test content");
        Long savePostId = postService.saveRecruitmentPost(recruitmentPostDto, null);

        Teaming post = (Teaming) postJpaRepository.findById(savePostId).get();

        Assertions.assertThat(post.getTitle())
                .isEqualTo(recruitmentPostDto.getTitle());
    }
}