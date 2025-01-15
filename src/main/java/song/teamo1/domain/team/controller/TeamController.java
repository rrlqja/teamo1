package song.teamo1.domain.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo1.domain.common.exception.common.IllegalRequestException;
import song.teamo1.domain.team.dto.ApplicationFormDto;
import song.teamo1.domain.team.dto.ReqCreateTeamDto;
import song.teamo1.domain.team.dto.ReqEditTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamDto;
import song.teamo1.domain.team.dto.ResGetTeamListTeamMemberDto;
import song.teamo1.domain.team.dto.UserTeamListDto;
import song.teamo1.domain.team.service.ResCreateTeamDto;
import song.teamo1.domain.team.service.TeamService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @GetMapping
    public String getCreateTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @ModelAttribute(name = "team") ReqCreateTeamDto reqCreateTeamDto) {
        if (userDetails == null) {
            throw new IllegalRequestException("잘못된 요청입니다.");
        }

        return "team/createTeam";
    }

    @PostMapping
    public String postCreateTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                 @ModelAttribute(name = "team") ReqCreateTeamDto reqCreateTeamDto,
                                 RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            throw new IllegalRequestException("잘못된 요청입니다.");
        }

        ResCreateTeamDto teamDto = teamService.createTeam(userDetails.getUser(), reqCreateTeamDto);
        redirectAttributes.addAttribute("teamId", teamDto.getId());

        return "redirect:/team/{teamId}";
    }

    @GetMapping("/teamList")
    public String getTeamList(@AuthenticationPrincipal UserDetailsImpl userDetails,
                              @PageableDefault(page = 0, size = 10) Pageable pageable,
                              Model model) {
        if (userDetails == null) {
            throw new IllegalRequestException("잘못된 요청입니다.");
        }

        Page<UserTeamListDto> userTeamList = teamService.getUserTeamList(userDetails.getUser(), pageable);

        model.addAttribute("teamList", userTeamList);

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

    @GetMapping("/{teamId}/edit")
    public String getEditTeam(@PathVariable("teamId") Long teamId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                              Model model) {
        if (userDetails == null) {
            throw new IllegalRequestException("잘못된 요청입니다.");
        }

        ResGetTeamDto teamDto = teamService.getTeam(userDetails.getUser(), teamId);

        model.addAttribute("team", teamDto);

        return "team/editTeam";
    }

    @PostMapping("/{teamId}/edit")
    public String postEditTeam(@PathVariable("teamId") Long teamId,
                               @AuthenticationPrincipal UserDetailsImpl userDetails,
                               ReqEditTeamDto reqEditTeamDto,
                               RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            throw new IllegalRequestException("잘못된 요청입니다.");
        }

        Long editTeamId = teamService.editTeam(userDetails.getUser(), teamId, reqEditTeamDto);
        redirectAttributes.addAttribute("teamId", editTeamId);

        return "redirect:/team/{teamId}";
    }

    @GetMapping("/{teamId}/applicationForm")
    public String getTeamApplicationForm(@PathVariable("teamId") Long teamId,
                                         @AuthenticationPrincipal UserDetailsImpl userDetails,
                                         @ModelAttribute("applicationForm") ApplicationFormDto applicationFormDto) {
//        if (userDetails == null) {
//            throw new IllegalRequestException("잘못된 요청입니다.");
//        }

        return "team/applicationForm";
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/{teamId}/applicationForm")
    public void postTeamApplicationForm(@PathVariable("teamId") Long teamId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        ApplicationFormDto applicationFormDto) {
//        if (userDetails == null) {
//            throw new IllegalRequestException("잘못된 요청입니다.");
//        }

        teamService.createApplication(userDetails.getUser(), teamId, applicationFormDto);
    }
}
