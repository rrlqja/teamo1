package song.teamo1.domain.team.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class TeamMemberServiceTest {
    @Autowired
    TeamMemberService teamMemberService;
    @Autowired
    TeamMemberJpaRepository teamMemberJpaRepository;

    @Test
    void successSaveTeamMemberByNull() {
        Long teamMemberId = teamMemberService.saveTeamMember(null, null);

        TeamMember teamMember = teamMemberJpaRepository.findById(teamMemberId)
                .get();

        assertThat(teamMember.getUser())
                .isNull();
    }

}