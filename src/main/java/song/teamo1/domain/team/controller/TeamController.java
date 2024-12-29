package song.teamo1.domain.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamDto;
import song.teamo1.domain.team.dto.ResTeamDto;
import song.teamo1.domain.team.service.ResCreateTeamDto;
import song.teamo1.domain.team.service.TeamService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ResTeamDto> getTeams(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<ResTeamDto> res = teamService.getTeams(userDetails.getUser());

        return res;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResCreateTeamDto createTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestBody ReqCreateTeamDto reqCreateTeamDto) {
        ResCreateTeamDto res = teamService.createTeam(userDetails.getUser(), reqCreateTeamDto);

        return res;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{teamId}")
    public ResGetTeamDto getTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @PathVariable("teamId") Long teamId) {
        ResGetTeamDto res = teamService.getTeam(userDetails.getUser(), teamId);

        return res;
    }
}
