package song.teamo1.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.post.SavePostDto;
import song.teamo1.domain.post.entity.Post;
import song.teamo1.domain.post.repository.PostJpaRepository;
import song.teamo1.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepository postJpaRepository;

    @Transactional
    void saveRecruitmentPost(SavePostDto savePostDto, User user) {
        Post recruitment = Post.createRecruitment(savePostDto.getTitle(), savePostDto.getContent(), user);

        Post savePost = postJpaRepository.save(recruitment);
    }
}
