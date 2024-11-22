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
    private final InitData initData;

    @PostConstruct
    public void setInit() {
        initData.init();
    }

    @Slf4j
    @Component
    @RequiredArgsConstructor
    private static class InitData {
        private final UserService userService;

        public void init() {
            userService.saveUser(new SaveUserDto("1", "1", "n1"));
        }
    }
}
