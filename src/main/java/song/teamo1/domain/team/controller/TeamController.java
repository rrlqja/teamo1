package song.teamo1.domain.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamListDto;
import song.teamo1.domain.team.dto.ResGetTeamListTeamMemberDto;
import song.teamo1.domain.team.dto.ResTeamDto;
import song.teamo1.domain.team.service.ResCreateTeamDto;
import song.teamo1.domain.team.service.TeamService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

import java.util.List;

@Slf4j
//@RestController
@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public String getTeams(@PageableDefault(value = 1, size = 10) Pageable pageable,
                           Model model) {
        List<ResGetTeamListDto> teamList = teamService.getTeamList(pageable);

        model.addAttribute("teamList", teamList);

        return "team/teamList";
    }

    @GetMapping("/{teamId}")
    public String getTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                          @PathVariable("teamId") Long teamId,
                          Model model) {
        ResGetTeamDto teamDto = null;
        if (userDetails == null) {
            teamDto = teamService.getTeam(teamId);
        } else {
            teamDto = teamService.getTeam(userDetails.getUser(), teamId);
        }

        List<ResGetTeamListTeamMemberDto> teamMemberList = teamService.getTeamMembers(teamDto.getTeamId());

        model.addAttribute("team", teamDto);
        model.addAttribute("teamMemberList", teamMemberList);

        return "team/team";
    }

//    @GetMapping("/user")
//    public String getUserTeams(@AuthenticationPrincipal UserDetailsImpl userDetails,
//                               Model model) {
//        List<ResTeamDto> res = teamService.getUserTeams(userDetails.getUser());
//
//        model.addAttribute("teamList", res);
//
//        return "team/teamList";
//    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public ResCreateTeamDto createTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestBody ReqCreateTeamDto reqCreateTeamDto) {
        ResCreateTeamDto res = teamService.createTeam(userDetails.getUser(), reqCreateTeamDto);

        return res;
    }
}
