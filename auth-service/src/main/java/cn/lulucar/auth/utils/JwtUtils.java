package cn.lulucar.auth.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author wxl
 * @date 2025/5/10 20:30
 * @description
 */
@Component
public class JwtUtils {
    // 建议从配置中读取
    private final String secret = "your-secret-key";
    // 24小时
    private final long expiration = 86400000;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token);
        return jwt.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
