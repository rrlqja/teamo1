package song.teamo1.security.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class JwtUtil {
    private final static SecretKey KEY = Jwts.SIG.HS256.key().build();

    public static String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = now.plusHours(1);

        return Jwts.builder()
                .issuedAt(Timestamp.valueOf(now))
                .expiration(Timestamp.valueOf(expiryTime))
                .claim("username", username)
                .signWith(KEY)
                .compact();

    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
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
