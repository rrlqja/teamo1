package song.teamo1.domain.team.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.application.dto.ResApplicationListDto;
import song.teamo1.domain.common.exception.application.exceptions.AlreadyExistsApplicantException;
import song.teamo1.domain.common.exception.application.exceptions.ApproveNotAllowedException;
import song.teamo1.domain.common.exception.application.exceptions.InvalidWriterException;
import song.teamo1.domain.common.exception.common.IllegalRequestException;
import song.teamo1.domain.common.exception.teammember.exceptions.DuplicateTeamMemberException;
import song.teamo1.domain.team.dto.ReqCreateApplicationDto;
import song.teamo1.domain.team.entity.Application;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.repository.ApplicationJpaRepository;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.repository.UserJpaRepository;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static song.teamo1.domain.team.entity.Application.Status.REJECTED;

@Slf4j
@SpringBootTest
@TestPropertySource(properties = {
        "JASYPT_ENCRYPTOR_PASSWORD=song"
})
class ApplicationServiceTest {
    @Autowired
    ApplicationService applicationService;
    @Autowired
    UserJpaRepository userRepository;
    @Autowired
    TeamJpaRepository teamRepository;
    @Autowired
    ApplicationJpaRepository applicationRepository;
    @Autowired
    TeamMemberJpaRepository teamMemberRepository;
    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        applicationRepository.deleteAll();
    }

    @DisplayName("신청서 생성 성공")
    @MethodSource("successCreateApplicationParam")
    @ParameterizedTest
    void successCreateApplication(Long userId, Long teamId) {
        User user = getUser(userId);
        Team team = getTeam(teamId);

        ReqCreateApplicationDto reqCreateApplicationDto = new ReqCreateApplicationDto("test application", "test application content");

        Long applicationId = applicationService.save(user, team.getId(), reqCreateApplicationDto);

        assertThat(applicationId)
                .isEqualTo(1L);
    }

    @DisplayName("이미 가입한 팀에 신청서 생성 시 예외")
    @MethodSource("failCreateApplicationParam")
    @ParameterizedTest
    void failCreateApplication(Long userId, Long teamId) {
        User user = getUser(userId);
        Team team = getTeam(teamId);

        ReqCreateApplicationDto reqCreateApplicationDto = new ReqCreateApplicationDto("test application", "test application content");

        assertThatThrownBy(() -> applicationService.save(user, team.getId(), reqCreateApplicationDto))
                .isInstanceOf(DuplicateTeamMemberException.class);
    }

    @DisplayName("중복된 신청서 생성시 예외")
    @MethodSource("successCreateApplicationParam")
    @ParameterizedTest
    void duplicateCreateApplication(Long userId, Long teamId) {
        User user = getUser(userId);
        Team team = getTeam(teamId);

        ReqCreateApplicationDto reqCreateApplicationDto = new ReqCreateApplicationDto("test application", "test application content");

        Long applicationId = applicationService.save(user, team.getId(), reqCreateApplicationDto);

        assertThatThrownBy(() -> {
                    applicationService.save(user, team.getId(), reqCreateApplicationDto);
                    applicationService.save(user, team.getId(), reqCreateApplicationDto);
                }
        ).isInstanceOf(AlreadyExistsApplicantException.class);
    }

    @DisplayName("신청서 취소 성공")
    @MethodSource("successCreateApplicationParam")
    @ParameterizedTest
    void successCancelApplication(Long userId, Long teamId) {
        User user = getUser(userId);
        Team team = getTeam(teamId);

        ReqCreateApplicationDto reqCreateApplicationDto = new ReqCreateApplicationDto("test application", "test application content");

        Long applicationId = applicationService.save(user, team.getId(), reqCreateApplicationDto);

        assertDoesNotThrow(() -> applicationService.cancelApplication(user, applicationId));
    }

    @DisplayName("작성자가 아닌 신청서 취소시 예외 발생")
    @MethodSource("successCreateApplicationParam")
    @ParameterizedTest
    void failCancelApplication(Long userId, Long teamId) {
        User user = getUser(userId);
        Team team = getTeam(teamId);

        ReqCreateApplicationDto reqCreateApplicationDto = new ReqCreateApplicationDto("test application", "test application content");

        Long applicationId = applicationService.save(user, team.getId(), reqCreateApplicationDto);

        User otherUser = getUser(2L);
        assertThatThrownBy(() -> applicationService.cancelApplication(otherUser, applicationId))
                .isInstanceOf(InvalidWriterException.class);
    }

    @DisplayName("특정 팀 신청서 리스트 조회")
    @MethodSource("getApplicationList")
    @ParameterizedTest
    void successGetApplicationList(Long userId, Long teamId) {
        User otherUser = getUser(userId);
        Team team = getTeam(teamId);

        Long applicationId = applicationService.save(otherUser, team.getId(), new ReqCreateApplicationDto("test application", "test application content"));

        User user = getUser(1L);

        List<ResApplicationListDto> applicationList = applicationService.getApplicationList(user, team.getId());
        assertThat(applicationList.size())
                .isEqualTo(1L);
    }

    @DisplayName("리더가 아닌 사용자가 신청서 목록 조회 시 예외")
    @MethodSource("noLeader")
    @ParameterizedTest
    void failGetApplicationListByNoLeader(Long userId, Long teamId) {
        User user = getUser(userId);
        Team team = getTeam(teamId);

        assertThatThrownBy(() -> applicationService.getApplicationList(user, team.getId()))
                .isInstanceOf(IllegalRequestException.class);
    }

    @Transactional
    @DisplayName("신청서 승인 성공")
    @MethodSource("approveApplication")
    @ParameterizedTest
    void successApproveApplication(Long userId, Long teamId) {
        User otherUser = getUser(userId);
        Team team = getTeam(teamId);

        Long applicationId = applicationService.save(otherUser, team.getId(), new ReqCreateApplicationDto("test application", "test application content"));

        User user = getUser(1L);

        Long teamMemberId = applicationService.approveApplication(user, applicationId);

        TeamMember teamMember = teamMemberRepository.findById(teamMemberId).get();
        assertThat(teamMember.getTeam().getId())
                .isEqualTo(team.getId());
    }

    @DisplayName("팀 리더가 아닌 사용자가 신청서 수랑 시 예외")
    @MethodSource("approveApplication")
    @ParameterizedTest
    void failApproveApplicationByNoLeader(Long userId, Long teamId) {
        User otherUser = getUser(userId);
        Team team = getTeam(teamId);

        Long applicationId = applicationService.save(otherUser, team.getId(), new ReqCreateApplicationDto("test application", "test application content"));

        User user = getUser(userId);

        assertThatThrownBy(() -> applicationService.approveApplication(user, applicationId))
                .isInstanceOf(ApproveNotAllowedException.class);
    }

    @DisplayName("신청서 거절 성공")
    @MethodSource("rejectApplication")
    @ParameterizedTest
    void successRejectApplication(Long userId, Long teamId) {
        User otherUser = getUser(userId);
        Team team = getTeam(teamId);

        Long applicationId = applicationService.save(otherUser, team.getId(), new ReqCreateApplicationDto("test application", "test application content"));

        User user = getUser(1L);

        assertDoesNotThrow(() -> applicationService.rejectApplication(user, applicationId));
        assertThat(applicationRepository.findById(applicationId).get().getStatus())
                .isEqualTo(REJECTED);
    }

    @DisplayName("리더가 아닌 사용자가 신청서 거절 시 예외")
    @MethodSource("failRejectApplication")
    @ParameterizedTest
    void failRejectApplicationByNoLeader(Long userId, Long teamId) {
        User otherUser = getUser(userId);
        Team team = getTeam(teamId);

        Long applicationId = applicationService.save(otherUser, team.getId(), new ReqCreateApplicationDto("test application", "test application content"));

        User user = getUser(userId);

        assertThatThrownBy(() -> applicationService.rejectApplication(user, applicationId))
                .isInstanceOf(ApproveNotAllowedException.class);
    }

    private static Stream<Arguments> successCreateApplicationParam() {
        return Stream.of(
                Arguments.of(1L, 2L)
        );
    }

    private static Stream<Arguments> failCreateApplicationParam() {
        return Stream.of(
                Arguments.of(1L, 1L)
        );
    }

    private static Stream<Arguments> getApplicationList() {
        return Stream.of(
                Arguments.of(2L, 1L)
        );
    }

    private static Stream<Arguments> noLeader() {
        return Stream.of(
                Arguments.of(1L, 2L)
        );
    }

    private static Stream<Arguments> approveApplication() {
        return Stream.of(
                Arguments.of(2L, 1L)
        );
    }

    private static Stream<Arguments> rejectApplication() {
        return Stream.of(
                Arguments.of(2L, 1L)
        );
    }

    private static Stream<Arguments> failRejectApplication() {
        return Stream.of(
                Arguments.of(2L, 1L)
        );
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    private Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).get();
    }

}