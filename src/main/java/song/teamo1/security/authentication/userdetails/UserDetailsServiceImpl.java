package song.teamo1.security.authentication.userdetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import song.teamo1.domain.user.entity.User;
import song.teamo1.domain.user.exception.UserNotFoundException;
import song.teamo1.domain.user.repository.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJpaRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        return new UserDetailsImpl(user);
    }
}
