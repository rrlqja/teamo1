package song.teamo1.domain.team.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import song.teamo1.domain.common.exception.team.exceptions.DuplicateTeamNameException;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamDto;
import song.teamo1.domain.team.dto.ResTeamDto;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.repository.UserJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class TeamServiceTest {
    @Autowired
    TeamService teamService;
    @Autowired
    TeamJpaRepository teamRepository;
    @Autowired
    TeamMemberJpaRepository teamMemberRepository;
    @Autowired
    UserJpaRepository userRepository;

//    @AfterEach
//    void afterEach() {
//        teamMemberRepository.deleteAll();
//        teamRepository.deleteAll();
//    }

    @Test
    @DisplayName("팀 생성 성공")
    void successCreateTeam() {
        ReqCreateTeamDto reqCreateTeamDto = new ReqCreateTeamDto("test team name", "test team info");

        ResCreateTeamDto res = teamService.createTeam(null, reqCreateTeamDto);

        Team team = teamRepository.findById(res.getId()).get();
        assertThat(team.getName())
                .isEqualTo(reqCreateTeamDto.getName());
    }

    @ValueSource(strings = {"1"})
    @ParameterizedTest
    @DisplayName("팀 생성 실패: 중복된 팀 이름")
    void failCreateTeam(String teamName) {
        ReqCreateTeamDto reqCreateTeamDto = new ReqCreateTeamDto(teamName, "test team info");

        assertThatThrownBy(() -> teamService.createTeam(null, reqCreateTeamDto))
                .isInstanceOf(DuplicateTeamNameException.class);
    }

//    @Test
//    @DisplayName("가입한 팀 조회 성공")
//    void successGetUserTeams() {
//        List<ResTeamDto> teams = teamService.getUserTeams(getUser());
//
//        Assertions.assertThat(teams.size())
//                .isEqualTo(1);
//    }

    @Test
    @DisplayName("팀 상세 조회")
    void successGetTeam() {
        ResGetTeamDto team = teamService.getTeam(getUser(), 1L);

        log.info(team.toString());

        assertThat(team.isAdmin())
                .isTrue();
    }

    User getUser() {
        return userRepository.findById(1L).get();
    }

}