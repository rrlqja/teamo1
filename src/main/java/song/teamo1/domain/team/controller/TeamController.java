package song.teamo1.domain.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.service.ResCreateTeamDto;
import song.teamo1.domain.team.service.TeamService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResCreateTeamDto createTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestBody ReqCreateTeamDto reqCreateTeamDto) {
        ResCreateTeamDto res = teamService.createTeam(userDetails.getUser(), reqCreateTeamDto);

        return res;
    }
}
