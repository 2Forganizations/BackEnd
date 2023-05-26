package project.travelmate;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import project.travelmate.request.AuthInfo;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {

        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        AuthInfo authInfo = new AuthInfo(annotation.username());
        final UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(authInfo, null, null);

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
