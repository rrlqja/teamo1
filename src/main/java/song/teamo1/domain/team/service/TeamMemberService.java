package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeamMemberService {
    private final TeamMemberJpaRepository teamMemberRepository;

    @Transactional
    public Long createTeamMemberLeader(User user, Team team) {
        Optional<TeamMember> teamMemberOptional = teamMemberRepository.findTeamMemberByTeamAndUser(team, user);
        if (teamMemberOptional.isPresent()) {
            return teamMemberOptional.get().getId();
        }

        TeamMember teamMember = TeamMember.create(team, user, TeamMember.TEAM_ROLE.LEADER);
        TeamMember saveTeamMember = teamMemberRepository.save(teamMember);
        return saveTeamMember.getId();
    }

    public List<Team> getTeamMembers(User user) {
        return teamMemberRepository.getTeamMembersByUser(user)
                .stream().map(TeamMember::getTeam)
                .toList();
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

    public List<TeamMember> getTeamMembersByTeamId(Team team) {
        return teamMemberRepository.findTeamMemberByTeam(team);
    }
}
