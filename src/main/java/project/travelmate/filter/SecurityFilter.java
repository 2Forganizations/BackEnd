package project.travelmate.filter;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.travelmate.advice.exception.ExpiredTokenException;
import project.travelmate.repository.UserRepository;
import project.travelmate.request.AuthInfo;
import project.travelmate.util.SecurityUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static project.travelmate.advice.ExceptionCodeConst.EXPIRED_ACCESS_TOKEN_CODE;

@Slf4j
@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;

    public SecurityFilter(SecurityUtil securityUtil, UserRepository userRepository) {
        this.securityUtil = securityUtil;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                if (securityUtil.isExpiration(token)) {
                    throw new ExpiredTokenException(EXPIRED_ACCESS_TOKEN_CODE);
                }
                String memberId = (String) securityUtil.get(token).get("id");
                String provider = (String) securityUtil.get(token).get("provider");

                AuthInfo authInfo = new AuthInfo(memberId, provider, token);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authInfo, null, null));
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException e) {
            if (e.getMessage().equalsIgnoreCase(EXPIRED_ACCESS_TOKEN_CODE.getMessage())) {
                JSONObject jsonObject = createJsonError(String.valueOf(UNAUTHORIZED.value()), e.getMessage());
                setJsonResponse(response, UNAUTHORIZED, jsonObject.toString());
            }
        } catch (Exception e) {
            writeErrorLogs("Exception", e.getMessage(), e.getStackTrace());

            if (response.getStatus() == HttpStatus.OK.value()) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
        } finally {
            log.debug("**** SECURITY FILTER FINISH");
        }
    }

    private JSONObject createJsonError(String errorCode, String errorMessage) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error_code", errorCode);
        jsonObject.put("error_message", errorMessage);

        return jsonObject;
    }

    private void setJsonResponse(HttpServletResponse response, HttpStatus httpStatus, String jsonValue) {
        response.setStatus(httpStatus.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        try {
            response.getWriter().write(jsonValue);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException ex) {
            writeErrorLogs("IOException", ex.getMessage(), ex.getStackTrace());
        }
    }

    private void writeErrorLogs(String exception, String message, StackTraceElement[] stackTraceElements) {
        log.error("**** " + exception + " ****");
        log.error("**** error message : " + message);
        log.error("**** stack trace : " + Arrays.toString(stackTraceElements));
    }

}