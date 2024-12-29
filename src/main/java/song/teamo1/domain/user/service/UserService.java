package song.teamo1.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import song.teamo1.domain.user.dto.SaveUserDto;
import song.teamo1.domain.user.entity.Role;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.repository.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(SaveUserDto saveUserDto) {
        findByUsername(saveUserDto.getUsername());

        User newUser = User.create(saveUserDto.getUsername(), passwordEncoder.encode(saveUserDto.getPassword()), saveUserDto.getName(), Role.USER);

        User saveUser = userRepository.save(newUser);
    }

    @Transactional
    public void getUser(User user) {
        userRepository.findById(user.getId());
    }

    private void findByUsername(String username) {
        userRepository.findByUsername(username)
                .ifPresent((user) -> {
                    throw new RuntimeException("사용자 있음");
                });
    }
}
