package project.travelmate.util;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.travelmate.domain.enums.AuthProvider;

import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtil {

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;

    @Value("jwt.auth.token-secret")
    private String secret;

    public String createAccessToken(String id, AuthProvider provider, String accessToken) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("id", id);
        claim.put("provider", provider);
        claim.put("accessToken", accessToken);
        return createJwt("ACCESS_TOKEN", ACCESS_TOKEN_EXPIRATION_TIME, claim);
    }

    public String createRefreshToken(String id, AuthProvider provider, String refreshToken) {
        HashMap<String, Object> claim = new HashMap<>();
        claim.put("id", id);
        claim.put("provider", provider);
        claim.put("refreshToken", refreshToken);
        return createJwt("REFRESH_TOKEN", REFRESH_TOKEN_EXPIRATION_TIME, claim);
    }

    public String createJwt(String subject, Long expiration, HashMap<String, Object> claim) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret);

        if (claim != null) {
            jwtBuilder.setClaims(claim);
        }

        if (expiration != null) {
            jwtBuilder.setExpiration(new Date(new Date().getTime() + expiration));
        }

        return jwtBuilder.compact();
    }

    public Claims get(String jwt) throws JwtException {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public boolean isExpiration(String jwt) throws JwtException {
        try {
            return get(jwt).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

}