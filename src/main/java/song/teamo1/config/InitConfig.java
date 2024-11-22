package song.teamo1.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitConfig {
    private final InitData initData;

    @PostConstruct
    public void setInit() {
    }

    @Slf4j
    @Component
    @RequiredArgsConstructor
    private static class InitData {
    }
}
