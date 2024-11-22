package song.teamo1.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import song.teamo1.domain.user.dto.SaveUserDto;
import song.teamo1.domain.user.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/save")
    public String saveUser(@ModelAttribute SaveUserDto saveUserDto) {
        userService.saveUser(saveUserDto);

        return "redirect:/login";
    }
}
