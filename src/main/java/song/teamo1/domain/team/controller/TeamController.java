package song.teamo1.domain.team.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import song.teamo1.domain.team.dto.CreateTeamDto;
import song.teamo1.domain.team.service.TeamService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/team")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("/save")
    public String createTeam(@AuthenticationPrincipal UserDetailsImpl userDetails,
                             @ModelAttribute CreateTeamDto createTeamDto,
                             RedirectAttributes redirectAttributes) {
        Long teamId = teamService.createTeam(userDetails.getUser(), createTeamDto);

        redirectAttributes.addFlashAttribute("teamId", teamId);

        return "redirect:/team/{teamId}";
    }
}
