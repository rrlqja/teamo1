package song.teamo1.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.post.dto.ResCreateTeamingDto;
import song.teamo1.domain.post.dto.ReqCreateTeamingDto;
import song.teamo1.domain.post.entity.Teaming;
import song.teamo1.domain.post.repository.PostJpaRepository;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.exception.TeamMemberNotFoundException;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final TeamMemberJpaRepository teamMemberJpaRepository;

    @Transactional
    public ResCreateTeamingDto createTeaming(User user, ReqCreateTeamingDto reqCreateTeamingDto) {
        TeamMember teamMember = teamMemberJpaRepository.findTeamMemberByTeamIdAndUserId(reqCreateTeamingDto.getTeamId(), user.getId())
                .orElseThrow(TeamMemberNotFoundException::new);

        Teaming teaming = reqCreateTeamingDto.toEntity(user ,teamMember.getTeam());

        Teaming savePost = postJpaRepository.save(teaming);

        return new ResCreateTeamingDto(savePost.getId());
    }
}
