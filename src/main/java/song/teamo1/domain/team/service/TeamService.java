package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.team.dto.CreateTeamDto;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.exception.DuplicateTeamNameException;
import song.teamo1.domain.team.exception.TeamNotFoundException;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamJpaRepository teamJpaRepository;
    private final TeamMemberService teamMemberService;

    @Transactional
    public Long createTeam(User user, CreateTeamDto createTeamDto) {
        validateTeamName(createTeamDto.getName());

        Team team = createTeamDto.toEntity();

        Team saveTeam = teamJpaRepository.save(team);

        Long teamMemberId = teamMemberService.saveTeamMember(user, saveTeam);

        return saveTeam.getId();
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
