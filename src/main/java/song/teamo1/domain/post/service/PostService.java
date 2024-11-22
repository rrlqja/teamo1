package song.teamo1.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.post.dto.SaveCommonPostDto;
import song.teamo1.domain.post.dto.SaveRecruitmentPostDto;
import song.teamo1.domain.post.entity.CommonPost;
import song.teamo1.domain.post.entity.RecruitmentPost;
import song.teamo1.domain.post.repository.PostJpaRepository;
import song.teamo1.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepository postJpaRepository;
//    private final RecruitmentPostJpaRepository recruitmentPostJpaRepository;

    @Transactional
    public Long saveRecruitmentPost(SaveRecruitmentPostDto recruitmentPostDto, User user) {
        RecruitmentPost recruitmentPost = recruitmentPostDto.toRecruitmentPost(user);

//        RecruitmentPost savePost = recruitmentPostJpaRepository.save(recruitmentPost);
        RecruitmentPost savePost = postJpaRepository.save(recruitmentPost);

        return savePost.getId();
    }

    @Transactional
    public Long saveCommonPost(SaveCommonPostDto saveCommonPostDto, User user) {
        CommonPost commonPost = saveCommonPostDto.toCommonPost(user);

//        postJpaRepository.
        return null;
    }
}
