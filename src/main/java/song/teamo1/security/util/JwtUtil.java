package song.teamo1.security.util;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class JwtUtil {
    private final static SecretKey KEY = Jwts.SIG.HS256.key().build();

    public static String generateToken(Long userId, String username, List<String> roles) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plusHours(1);

        return Jwts.builder()
                .issuedAt(Timestamp.valueOf(now))
                .expiration(Timestamp.valueOf(expiryTime))
                .claim("id", userId)
                .claim("username", username)
                .claim("roles", roles)
                .signWith(KEY)
                .compact();

    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            throw e;
        }

    }

    public static String getUsername(String token) {
        return Jwts.parser().verifyWith(KEY).build()
                .parseSignedClaims(token)
                .getPayload()
                .get("username")
                .toString();
    }
}
