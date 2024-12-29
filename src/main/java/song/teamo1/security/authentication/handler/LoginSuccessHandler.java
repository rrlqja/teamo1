package song.teamo1.security.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;
import song.teamo1.security.util.JwtUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = JwtUtil.generateToken(principal.getUser().getId(), principal.getUsername(), principal.getAuthorities().stream().map(Object::toString).toList());
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", jwt);

        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(tokens));
    }
}
