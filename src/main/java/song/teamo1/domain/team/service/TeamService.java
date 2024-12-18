package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamDto;
import song.teamo1.domain.team.dto.ResTeamDto;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.common.exception.team.exceptions.DuplicateTeamNameException;
import song.teamo1.domain.common.exception.team.exceptions.TeamNotFoundException;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.user.entity.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamJpaRepository teamRepository;
    private final TeamMemberService teamMemberService;

    public List<ResTeamDto> getTeams(User user) {
        List<Team> teamList = teamMemberService.getTeamMembers(user);

        return teamList.stream().map(ResTeamDto::new)
                .toList();
    }

    @Transactional
    public ResCreateTeamDto createTeam(User user, ReqCreateTeamDto reqCreateTeamDto) {
        validateTeamName(reqCreateTeamDto.getName());

        Team team = reqCreateTeamDto.toEntity();

        Team saveTeam = teamRepository.save(team);

        Long teamMemberId = teamMemberService.createTeamMemberLeader(user, saveTeam);

        return new ResCreateTeamDto(saveTeam.getId());
    }

    private void validateTeamName(String name) {
        teamRepository.findTeamByName(name)
                .ifPresent(team -> {
                    throw new DuplicateTeamNameException("이미 존재하는 팀 이름 입니다.");
                });
    }

    public ResGetTeamDto getTeam(User user, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        List<TeamMember> teamMemberList = teamMemberService.getTeamMembersByTeamId(team);

        return new ResGetTeamDto(team,
                teamMemberList,
                teamMemberList.stream().anyMatch(teamMember ->
                        teamMember.getUser().getId().equals(user.getId()) &&
                                teamMember.getTeamRole() == TeamMember.TEAM_ROLE.LEADER ||
                                teamMember.getTeamRole() == TeamMember.TEAM_ROLE.SUB_LEADER));
    }
}
