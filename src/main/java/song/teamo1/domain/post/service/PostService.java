package song.teamo1.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.post.dto.ResCreateTeamingDto;
import song.teamo1.domain.post.dto.ReqCreateTeamingDto;
import song.teamo1.domain.post.dto.TeamingListDto;
import song.teamo1.domain.post.entity.Teaming;
import song.teamo1.domain.post.repository.PostJpaRepository;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.common.exception.teammember.exceptions.TeamMemberNotFoundException;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostJpaRepository postJpaRepository;
    private final TeamMemberJpaRepository teamMemberJpaRepository;

    @Transactional
    public Page<TeamingListDto> getTeamingList(Pageable pageable) {
        return postJpaRepository.findTeamingList(pageable)
                .map(TeamingListDto::new);
    }

    @Transactional
    public ResCreateTeamingDto createTeaming(User user, ReqCreateTeamingDto reqCreateTeamingDto) {
        TeamMember teamMember = teamMemberJpaRepository.findTeamMemberByTeamIdAndUserId(reqCreateTeamingDto.getTeamId(), user.getId())
                .orElseThrow(TeamMemberNotFoundException::new);

        Teaming teaming = reqCreateTeamingDto.toEntity(user ,teamMember.getTeam());

        Teaming savePost = postJpaRepository.save(teaming);

        return new ResCreateTeamingDto(savePost.getId());
    }
}
