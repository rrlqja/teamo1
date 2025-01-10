package song.teamo1.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.entity.TeamMember.TeamRole;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.Role;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.repository.UserJpaRepository;

import static song.teamo1.domain.team.entity.TeamMember.TeamRole.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final PasswordEncoder passwordEncoder;
        private final UserJpaRepository userRepository;
        private final TeamJpaRepository teamRepository;
        private final TeamMemberJpaRepository teamMemberRepository;

        public void init() {
            User user1 = userRepository.save(createUser("1", "1", "user1"));
            User user2 = userRepository.save(createUser("2", "2", "user2"));
            Team team1 = teamRepository.save(createTeam("team1", "team 1 info"));
            Team team2 = teamRepository.save(createTeam("team2", "team 2 info"));
            TeamMember teamMember1 = teamMemberRepository.save(createTeamMember(team1, user1));
            TeamMember grantTeamMember1 = grantRole(teamMember1, LEADER);
            TeamMember teamMember2 = teamMemberRepository.save(createTeamMember(team2, user2));
            TeamMember grantTeamMember2 = grantRole(teamMember2, LEADER);
        }

        private User createUser(String username, String password, String name) {
            return User.create(username, passwordEncoder.encode(password), name, Role.USER);
        }

        private Team createTeam(String name, String info) {
            return Team.create(name, info);
        }

        private TeamMember createTeamMember(Team team, User user) {
            return TeamMember.create(team, user);
        }

        private TeamMember grantRole(TeamMember teamMember, TeamRole teamRole) {
            teamMember.grantRole(teamRole);
            return teamMemberRepository.save(teamMember);
        }
    }
}
