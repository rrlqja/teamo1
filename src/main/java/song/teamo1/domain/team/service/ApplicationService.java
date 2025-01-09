package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.application.dto.ResApplicationListDto;
import song.teamo1.domain.common.exception.application.exceptions.AlreadyExistsApplicantException;
import song.teamo1.domain.common.exception.application.exceptions.ApplicationNotFoundException;
import song.teamo1.domain.common.exception.application.exceptions.ApproveNotAllowedException;
import song.teamo1.domain.common.exception.application.exceptions.InvalidWriterException;
import song.teamo1.domain.common.exception.common.IllegalRequestException;
import song.teamo1.domain.common.exception.team.exceptions.TeamNotFoundException;
import song.teamo1.domain.common.exception.teammember.exceptions.DuplicateTeamMemberException;
import song.teamo1.domain.team.dto.ReqCreateApplicationDto;
import song.teamo1.domain.team.entity.Application;
import song.teamo1.domain.team.entity.Team;
import song.teamo1.domain.team.entity.TeamMember;
import song.teamo1.domain.team.repository.ApplicationJpaRepository;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.team.repository.TeamMemberJpaRepository;
import song.teamo1.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

import static song.teamo1.domain.team.entity.Application.Status.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationJpaRepository applicationRepository;
    private final TeamJpaRepository teamRepository;
    private final TeamMemberJpaRepository teamMemberRepository;
    private final TeamMemberService teamMemberService;

    @Transactional
    public Long save(User user, Long teamId, ReqCreateApplicationDto applicationDto) {
        Team team = getTeam(teamId);

        teamMemberRepository.findTeamMemberByTeamAndUser(team, user)
                .ifPresent(teamMember -> {
                    throw new DuplicateTeamMemberException("이미 가입한 팀입니다.");
                });

        applicationRepository.findApplicationByTeamAndUserAndStatus(team, user, PENDING)
                .ifPresent(application -> {
                    throw new AlreadyExistsApplicantException("이미 신청하였습니다.");
                });

        Application application = applicationDto.toEntity(user, team);

        Application saveApplication = applicationRepository.save(application);

        return saveApplication.getId();
    }

    @Transactional
    public void cancelApplication(User user, Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(ApplicationNotFoundException::new);

        if (!application.isWriter(user)) {
            throw new InvalidWriterException("권한이 없습니다.");
        }

        applicationRepository.deleteById(applicationId);
    }

    @Transactional
    public List<ResApplicationListDto> getApplicationList(User user, Long teamId) {
        Team team = getTeam(teamId);

        List<TeamMember> teamMemberList = teamMemberService.getTeamMembersByTeam(team);
        teamMemberList.stream()
                .filter(tm -> tm.isTeamLeader(user))
                .findAny()
                .orElseThrow(() -> new IllegalRequestException("권한이 없습니다."));

        List<Application> applicationList = applicationRepository.findApplicationsByTeam(team, PENDING);

        return applicationList.stream()
                .map(ResApplicationListDto::new)
                .toList();
    }

    @Transactional
    public Long approveApplication(User user, Long applicationId) {
        Application application = getApplication(applicationId);

        Team team = application.getTeam();

        validateTeamLeader(user, team);

        application.accept();

        Long teamMemberId = teamMemberService.createTeamMember(application.getUser(), team);

        return teamMemberId;
    }

    @Transactional
    public void rejectApplication(User user, Long applicationId) {
        Application application = getApplication(applicationId);

        Team team = application.getTeam();

        validateTeamLeader(user, team);

        application.reject();
    }

    private Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    }

    private Application getApplication(Long applicationId) {
        return applicationRepository.findByIdAndStatus(applicationId, PENDING).orElseThrow(ApplicationNotFoundException::new);
    }

    private void validateTeamLeader(User user, Team team) {
        Optional<TeamMember> optionalTeamMember = teamMemberService.getTeamMembersByTeam(team).stream()
                .filter(tm -> tm.isTeamLeader(user))
                .findAny();

        if (optionalTeamMember.isEmpty()) {
            throw new ApproveNotAllowedException("권한이 없습니다.");
        }
    }
}
