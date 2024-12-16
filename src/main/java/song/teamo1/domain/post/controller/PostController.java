package song.teamo1.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo1.domain.post.dto.ResCreateTeamingDto;
import song.teamo1.domain.post.dto.ReqCreateTeamingDto;
import song.teamo1.domain.post.service.PostService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/teaming")
    public ResponseEntity<ResCreateTeamingDto> createTeaming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                             @RequestBody ReqCreateTeamingDto reqCreateTeamingDto) {
        ResCreateTeamingDto res = postService.createTeaming(userDetails.getUser(), reqCreateTeamingDto);

        return ResponseEntity.ok(res);
    }
}
