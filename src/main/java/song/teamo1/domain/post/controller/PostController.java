package song.teamo1.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo1.domain.post.dto.TeamingListDto;
import song.teamo1.domain.post.service.PostService;
import song.teamo1.domain.team.dto.CreateTeamingDto;
import song.teamo1.domain.team.service.TeamMemberService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final TeamMemberService teamMemberService;

    @GetMapping("/teamingList")
    public String createTeaming(@PageableDefault(value = 10, page = 0) Pageable pageable,
                                Model model) {
        Page<TeamingListDto> teamingList = postService.getTeamingList(pageable);

        model.addAttribute("teamingList", teamingList);

        return "post/teamingList";
    }

    @GetMapping("/teaming")
    public String createTeaming(@ModelAttribute("teaming") CreateTeamingDto createTeamingDto) {

        return "post/createTeaming";
    }
}
