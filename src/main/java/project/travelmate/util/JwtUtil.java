package project.travelmate.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import project.travelmate.domain.enums.AuthProvider;
import project.travelmate.request.AuthInfo;

import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class JwtUtil {
    public static final int SEC = 1000;
    public static final int MINUTE = 60 * SEC;
    public static final int HOUR = 60 * MINUTE;
    public static final int DAY = 24 * HOUR;

    public static final String TOKEN_HEADER_NAME = "Authorization";
    public static final String TOKEN_NAME_WITH_SPACE = "Bearer ";
    public static final String CLAIM_ID = "id";

    private static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 2L;
    private static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 30L;

    @Value("jwt.auth.token-secret")
    private String JWT_SECRET;

    public AuthInfo jwtToAuthInfo(String BearerToken) {
        String jwtToken = replaceBearer(BearerToken);
        DecodedJWT decodedJWT = verifyToken(jwtToken);
        return new AuthInfo(
                decodedJWT.getClaim(CLAIM_ID).asString()
        );
    }

    private String replaceBearer(String tokenString) {
        try {
            return tokenString.replace(TOKEN_NAME_WITH_SPACE, "");
        } catch (Exception e) {
            log.info("Bearer header 존재하지 않음");
            throw new IllegalArgumentException();
        }
    }

    private DecodedJWT verifyToken(String jwtToken) {
        try {
            return JWT
                    .require(Algorithm.HMAC512(JWT_SECRET))
                    .build()
                    .verify(jwtToken);
        } catch (AlgorithmMismatchException algorithmMismatchException){
            log.info("토큰 알고리즘 미스매칭");
            throw new IllegalArgumentException("토큰 알고리즘 미스매칭");
        } catch (SignatureVerificationException signatureVerificationException){
            log.info("토큰 signature verifying 에러");
            throw new IllegalArgumentException("토큰 signature verifying 에러");
        } catch (TokenExpiredException tokenExpiredException) {
            log.info("Access토큰 만료됨");
            throw new TokenExpiredException("토큰 만료됨");
        } catch (InvalidClaimException invalidClaimException) {
            log.info("토큰 클레임 에러");
            throw new IllegalArgumentException("토큰 클레임 에러");
        }
    }

    public String createAccessToken(String id) {
        String token = JWT.create()
                .withSubject("travel-mate")
                .withExpiresAt(new Date(System.currentTimeMillis() + (7 * DAY) ))
                .withClaim(CLAIM_ID, id)
                .sign(Algorithm.HMAC512(JWT_SECRET));   //secretkey
        return token;
    }

    public String createRefreshToken(String id) {
        String token = JWT.create()
                .withSubject("capin")
                .withExpiresAt(new Date(System.currentTimeMillis() + (14 * DAY) ))
                .withClaim(CLAIM_ID, id)
                .sign(Algorithm.HMAC512(JWT_SECRET));   //secretkey
        return token;
    }


//    public String createAccessToken(String id, AuthProvider provider, String accessToken) {
//        HashMap<String, Object> claim = new HashMap<>();
//        claim.put("id", id);
//        claim.put("provider", provider);
//        claim.put("accessToken", accessToken);
//        return createJwt("ACCESS_TOKEN", ACCESS_TOKEN_EXPIRATION_TIME, claim);
//    }
//
//    public String createRefreshToken(String id, AuthProvider provider, String refreshToken) {
//        HashMap<String, Object> claim = new HashMap<>();
//        claim.put("id", id);
//        claim.put("provider", provider);
//        claim.put("refreshToken", refreshToken);
//        return createJwt("REFRESH_TOKEN", REFRESH_TOKEN_EXPIRATION_TIME, claim);
//    }

}