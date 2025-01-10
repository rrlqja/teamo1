package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.common.exception.team.exceptions.EditNotAllowedException;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.dto.ReqEditTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamListDto;
import song.teamo1.domain.team.dto.ResGetTeamListTeamMemberDto;
import song.teamo1.domain.team.dto.ResTeamDto;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.common.exception.team.exceptions.DuplicateTeamNameException;
import song.teamo1.domain.common.exception.team.exceptions.TeamNotFoundException;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.user.entity.User;

import java.util.List;

import static song.teamo1.domain.team.entity.TeamMember.TeamRole.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamJpaRepository teamRepository;
    private final TeamMemberService teamMemberService;

    @Transactional
    public List<ResGetTeamListDto> getTeamList(Pageable pageable) {
        Page<Team> teamList = teamRepository.findAll(pageable);

        return teamList.map(team -> new ResGetTeamListDto(team, teamMemberService.getTeamLeader(team)))
                .toList();
    }

//    public List<ResTeamDto> getUserTeams(User user) {
//        List<Team> teamList = teamMemberService.getTeamMembers(user);
//
//        return teamList.stream().map(ResTeamDto::new)
//                .toList();
//    }

    @Transactional
    public ResCreateTeamDto createTeam(User user, ReqCreateTeamDto reqCreateTeamDto) {
        validateTeamName(reqCreateTeamDto.getName());

        Team team = reqCreateTeamDto.toEntity();

        Team saveTeam = teamRepository.save(team);

        Long teamMemberId = teamMemberService.createTeamMember(user, saveTeam);
        teamMemberService.grantTeamRole(teamMemberId, LEADER);

        return new ResCreateTeamDto(saveTeam.getId());
    }

    private void validateTeamName(String name) {
        teamRepository.findTeamByName(name)
                .ifPresent(team -> {
                    throw new DuplicateTeamNameException("이미 존재하는 팀 이름 입니다.");
                });
    }

    @Transactional
    public ResGetTeamDto getTeam(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        List<TeamMember> teamMemberList = teamMemberService.getTeamMembersByTeam(team);

        return new ResGetTeamDto(team,
                teamMemberList,
                false);
    }

    @Transactional
    public ResGetTeamDto getTeam(User user, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(TeamNotFoundException::new);

        List<TeamMember> teamMemberList = teamMemberService.getTeamMembersByTeam(team);

        boolean isAdmin = false;
        if (user != null) {
            isAdmin = teamMemberList.stream().anyMatch(teamMember ->
                    teamMember.getUser().getId().equals(user.getId()) &&
                    teamMember.getTeamRole() == LEADER ||
                    teamMember.getTeamRole() == SUB_LEADER);
        }

        return new ResGetTeamDto(team,
                teamMemberList,
                isAdmin);
    }

    @Transactional
    public List<ResGetTeamListTeamMemberDto> getTeamMembers(Long teamId) {
        return teamMemberService.getTeamMembers(teamId)
                .stream().map(ResGetTeamListTeamMemberDto::new)
                .toList();
    }

    @Transactional
    public Long editTeam(User user, Long teamId, ReqEditTeamDto reqEditTeamDto) {
        Team team = teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);

        List<TeamMember> teamMembers = teamMemberService.getTeamMembers(team.getId());
        teamMembers.stream().filter(tm -> tm.isTeamLeader(user))
                .findAny().orElseThrow(EditNotAllowedException::new);

        team.edit(reqEditTeamDto.getTeamName(), reqEditTeamDto.getTeamInfo());
        return teamRepository.save(team).getId();
    }
}
