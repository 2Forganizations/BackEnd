package project.travelmate.filter;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.travelmate.advice.exception.ExpiredTokenException;
import project.travelmate.advice.exception.UserNotFoundException;
import project.travelmate.repository.UserRepository;
import project.travelmate.util.SecurityUtil;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static project.travelmate.advice.ExceptionCodeConst.EXPIRED_ACCESS_TOKEN_CODE;
import static project.travelmate.advice.ExceptionCodeConst.USER_NOT_FOUND_CODE;
import static project.travelmate.domain.enums.AuthProvider.findByCode;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {

    private final SecurityUtil securityUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {

        try {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                if (securityUtil.isExpiration(token)) {
                    throw new ExpiredTokenException(EXPIRED_ACCESS_TOKEN_CODE);
                }

                String userId = (String) securityUtil.get(token).get("userId");
                String provider = (String) securityUtil.get(token).get("provider");

                if (userId == null ||
                        provider == null ||
                        !userRepository.existsByIdAndAuthProvider(userId, findByCode(provider))) {
                    throw new UserNotFoundException(USER_NOT_FOUND_CODE);
                }
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException e) {
            if (e.getMessage().equalsIgnoreCase(EXPIRED_ACCESS_TOKEN_CODE.getMessage())) {
                JSONObject jsonObject = createJsonError(String.valueOf(UNAUTHORIZED.value()), e.getMessage());
                setJsonResponse(response, UNAUTHORIZED, jsonObject.toString());
            }
        } catch (UserNotFoundException e) {
            if (e.getMessage().equalsIgnoreCase(USER_NOT_FOUND_CODE.getMessage())) {
                JSONObject jsonObject = createJsonError(String.valueOf(UNAUTHORIZED.value()), e.getMessage());
                setJsonResponse(response, UNAUTHORIZED, jsonObject.toString());
            }
        } catch (Exception e) {
            if (response.getStatus() == HttpStatus.OK.value()) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            }
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
    }

}