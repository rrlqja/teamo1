package song.teamo1.domain.team.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.repository.UserJpaRepository;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class TeamMemberServiceTest {
    @Autowired
    TeamMemberService teamMemberService;
    @Autowired
    TeamMemberJpaRepository teamMemberRepository;
    @Autowired
    UserJpaRepository userRepository;
    @Autowired
    TeamJpaRepository teamRepository;

    @Test
    void successCreateTeamMember() {
        Long teamMemberId = teamMemberService.createTeamMember(getUSer(), getTeam());

        TeamMember teamMember = teamMemberRepository.findById(teamMemberId)
                .get();

        assertThat(teamMember.getTeamRole())
                .isEqualTo(TeamMember.TeamRole.LEADER);
        assertThat(teamMember.getUser().getId())
                .isEqualTo(1L);
    }

    User getUSer() {
        return userRepository.findById(1L).get();
    }

    Team getTeam() {
        return teamRepository.findById(1L).get();
    }

}