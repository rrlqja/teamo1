package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.common.exception.team.exceptions.DuplicateTeamNameException;
import song.teamo1.domain.common.exception.team.exceptions.TeamNotFoundException;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamJpaRepository teamJpaRepository;
    private final TeamMemberService teamMemberService;

    @Transactional
    public ResCreateTeamDto createTeam(User user, ReqCreateTeamDto reqCreateTeamDto) {
        validateTeamName(reqCreateTeamDto.getName());

        Team team = reqCreateTeamDto.toEntity();

        Team saveTeam = teamJpaRepository.save(team);

        Long teamMemberId = teamMemberService.saveTeamMember(user, saveTeam);

        return new ResCreateTeamDto(saveTeam.getId());
    }

    private void validateTeamName(String name) {
        teamJpaRepository.findTeamByName(name)
                .ifPresent(team -> {
                    throw new DuplicateTeamNameException("이미 존재하는 팀 이름 입니다.");
                });
    }

    @Transactional
    public Team findTeamById(Long teamId) {
        return teamJpaRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);
    }
}
