package song.teamo1.domain.team.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.team.dto.ReqCreateApplicationDto;
import song.teamo1.domain.team.repository.ApplicationJpaRepository;
import song.teamo1.domain.team.repository.TeamJpaRepository;
import song.teamo1.domain.user.entity.User;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationJpaRepository applicationRepository;
    private final TeamJpaRepository teamRepository;

    @Transactional
    public void save(User user, Long teamId, ReqCreateApplicationDto applicationDto) {
        teamRepository.findById(teamId)
    }
}
