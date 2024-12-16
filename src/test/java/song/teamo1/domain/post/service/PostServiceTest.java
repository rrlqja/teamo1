package song.teamo1.domain.post.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.post.dto.ResCreateTeamingDto;
import song.teamo1.domain.post.dto.ReqCreateTeamingDto;
import song.teamo1.domain.post.entity.Teaming;
import song.teamo1.domain.post.repository.PostJpaRepository;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.repository.UserJpaRepository;

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
    @Autowired
    UserJpaRepository userJpaRepository;

    @Test
    void successCreateTeaming() {
        //given
        ReqCreateTeamingDto saveTeamingDto = new ReqCreateTeamingDto("test title", "test content", 1L);

        //when
        ResCreateTeamingDto res = postService.createTeaming(getUser(), saveTeamingDto);

        //then
        Assertions.assertThat(postJpaRepository.findById(res.getId()))
                .isNotEmpty()
                .get().isInstanceOf(Teaming.class);
    }

    private User getUser() {
        return userJpaRepository.findById(1L).get();
    }
}