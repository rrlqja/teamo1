package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.common.exception.teammember.exceptions.TeamMemberNotFoundException;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.entity.TeamMember.TeamRole;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

import static song.teamo1.domain.team.entity.TeamMember.TeamRole.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberService {
    private final TeamMemberJpaRepository teamMemberRepository;

    @Transactional
    public List<TeamMember> getTeamMembersByUser(User user) {
        return teamMemberRepository.findTeamMembersByUser(user);
    }

    @Transactional
    public TeamMember getTeamLeader(Team team) {
        return teamMemberRepository.findTeamLeader(team, LEADER).orElseThrow(TeamMemberNotFoundException::new);
    }

    @Transactional
    public Long createTeamMember(User user, Team team) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findTeamMemberByTeamAndUser(team, user);
        if (teamMemberOptional.isPresent()) {
            return teamMemberOptional.get().getId();
        }

        TeamMember teamMember = TeamMember.create(team, user);
        TeamMember saveTeamMember = teamMemberRepository.save(teamMember);
        return saveTeamMember.getId();
    }

    @Transactional
    public void grantTeamRole(Long teamMemberId, TeamRole teamRole) {
        TeamMember teamMember =
                teamMemberRepository.findById(teamMemberId).orElseThrow(TeamMemberNotFoundException::new);

        teamMember.grantRole(teamRole);
    }

    @Transactional
    public List<TeamMember> getTeamMembers(Long teamId) {
        return teamMemberRepository.getTeamMembersByTeam_Id(teamId);
    }

    public boolean isMember(Team team, User user) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findTeamMemberByTeamAndUser(team, user);

        if (optionalTeamMember.isPresent()) {
            optionalTeamMember.get()
                    .isTeamLeader(user);
            return true;
        }

        return false;
    }

    public List<TeamMember> getTeamMembersByTeam(Team team) {
        return teamMemberRepository.findTeamMemberByTeam(team);
    }
}
