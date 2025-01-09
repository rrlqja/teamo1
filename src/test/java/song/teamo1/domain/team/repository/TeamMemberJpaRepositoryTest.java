package song.teamo1.domain.team.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.repository.UserJpaRepository;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class TeamMemberJpaRepositoryTest {
    @Autowired
    TeamMemberJpaRepository teamMemberRepository;
    @Autowired
    UserJpaRepository userRepository;
    @Autowired
    TeamJpaRepository teamRepository;

    @Test
    void t1() {
        User user = userRepository.findById(1L).get();
        Team team = teamRepository.findById(2L).get();

        TeamMember teamMember = TeamMember.create(team, user);
        TeamMember save = teamMemberRepository.save(teamMember);

        TeamMember teamMember1 = teamMemberRepository.findById(1L).get();
        teamMember1.getTeam().getName();
    }

}