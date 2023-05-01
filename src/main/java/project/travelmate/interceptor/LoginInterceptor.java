package project.travelmate.interceptor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import project.travelmate.advice.exception.ExpiredTokenException;
import project.travelmate.request.AuthInfo;
import project.travelmate.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static project.travelmate.advice.ExceptionCodeConst.EXPIRED_ACCESS_TOKEN_CODE;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    public LoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            if (jwtUtil.isExpiration(token)) {
                throw new ExpiredTokenException(EXPIRED_ACCESS_TOKEN_CODE);
            }

            String id = (String) jwtUtil.get(token).get("id");
            String provider = (String) jwtUtil.get(token).get("provider");

            AuthInfo authInfo = new AuthInfo(id, provider, token);
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authInfo, null, null));
        }

        return true;
    }

}