package song.teamo1.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.post.dto.SaveCommonPostDto;
import song.teamo1.domain.post.dto.SavePostDto;
import song.teamo1.domain.post.dto.SaveTeamingDto;
import song.teamo1.domain.post.entity.CommonPost;
import song.teamo1.domain.post.entity.Teaming;
import song.teamo1.domain.post.repository.PostJpaRepository;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.team.service.TeamService;
import song.teamo1.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final TeamService teamService;
    private final PostJpaRepository postJpaRepository;
    private final TeamJpaRepository teamJpaRepository;
    private final TeamMemberJpaRepository teamMemberJpaRepository;

    @Transactional
    public void savePost(User user, SaveTeamingDto saveTeamingDto) {

    }

    @Transactional
    public Long saveRecruitmentPost(SaveTeamingDto saveTeamingDto, User user) {
        Team team = teamService.findTeamById(saveTeamingDto.getTeamId());
        teamMemberJpaRepository.findTeamMemberByTeamId
        Teaming teaming = saveTeamingDto.toTeaming(user);

        Teaming savePost = postJpaRepository.save(teaming);

        return savePost.getId();
    }

    @Transactional
    public Long saveCommonPost(SaveCommonPostDto saveCommonPostDto, User user) {
        CommonPost commonPost = saveCommonPostDto.toCommonPost(user);

        CommonPost savePost = postJpaRepository.save(commonPost);

        return savePost.getId();
    }

    @Transactional
    public void savePost(SavePostDto savePostDto, User user) {


//        savePostDto.toProjectPost()
    }
}
