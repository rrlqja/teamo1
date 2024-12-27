package song.teamo1.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import song.teamo1.security.authentication.userdetails.UserDetailsImpl;
import song.teamo1.security.util.JwtUtil;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            try {
                if (JwtUtil.validateToken(token)) {
                    String username = JwtUtil.getUsername(token);

                    new UserDetailsImpl()
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authenticated =
                            UsernamePasswordAuthenticationToken.authenticated(userDetails, null, userDetails.getAuthorities());
                    SecurityContext context = SecurityContextHolder.getContext();
                    context.setAuthentication(authenticated);
                }
            } catch (Exception e) {
                doResponse(response, "invalid jwt");
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private void doResponse(HttpServletResponse response, String message) {
        SecurityContextHolder.clearContext();

        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");

        try {
            response.getWriter().write(objectMapper.writeValueAsString(Map.of("message", message)));
        } catch (Exception e) {
            log.error("error: {}", e.getMessage());
        }
    }
}
