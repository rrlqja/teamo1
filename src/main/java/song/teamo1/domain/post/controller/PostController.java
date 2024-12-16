package song.teamo1.domain.post.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import song.teamo1.domain.post.dto.ResCreateTeamingDto;
import song.teamo1.domain.post.dto.ReqCreateTeamingDto;
import song.teamo1.domain.post.service.PostService;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/teaming")
    public ResCreateTeamingDto createTeaming(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @RequestBody ReqCreateTeamingDto reqCreateTeamingDto) {

        ResCreateTeamingDto res = postService.createTeaming(userDetails.getUser(), reqCreateTeamingDto);

        return res;
    }
}
