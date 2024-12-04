package song.teamo1.domain.team.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.team.dto.CreateTeamDto;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class TeamServiceTest {
    @Autowired
    TeamService teamService;
    @Autowired
    TeamJpaRepository teamJpaRepository;

    @AfterEach
    void afterEach() {
        teamJpaRepository.deleteAll();
    }

    @Test
    public void successCreateTeam() {
        CreateTeamDto createTeamDto = new CreateTeamDto("test team name", "test team info");

        Long teamId = teamService.createTeam(null, createTeamDto);

        Team team = teamJpaRepository.findById(teamId).get();
        assertThat(team.getName())
                .isEqualTo(createTeamDto.getName());
    }

}