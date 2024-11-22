package song.teamo1.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import song.teamo1.domain.user.dto.SaveUserDto;
import song.teamo1.domain.user.service.UserService;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitService initService;

    @PostConstruct
    public void setInit() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    private static class InitService {
        private final UserService userService;

        public void init() {
            userService.saveUser(new SaveUserDto("1", "1", "n1"));
        }
    }
}
