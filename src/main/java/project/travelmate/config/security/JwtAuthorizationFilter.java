package project.travelmate.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import project.travelmate.request.AuthInfo;
import project.travelmate.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static project.travelmate.util.JwtUtil.TOKEN_HEADER_NAME;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JwtUtil jwtTokenUtils) {
        super(authenticationManager);
        this.jwtUtil = jwtTokenUtils;
    }

    /**
     * 인증이나 권한이 필요할 시 아래의 필터를 탄다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(request, response);
        log.info("인증 시도중...");
        try {
            String bearerToken = request.getHeader(TOKEN_HEADER_NAME);
            AuthInfo authInfo = jwtUtil.jwtToAuthInfo(bearerToken);
//            ExpiredTokenException

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(authInfo, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.info("erorr !!!!!!!!!! class:{}, message: {}", e.getClass(), e.getMessage());

            request.setAttribute("error", e);
            request.setAttribute("type", "exception");
        } finally { //에러가 발생시 authenticationentrypoint로
            chain.doFilter(request, response);
        }
    }
}
