package project.travelmate.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {
    //UserDetailsService를 쓰지는 않지만, 구현체가 없다면 WebSecurityConfigurerAdapter에서 authenticationManager를 사용 불가능

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
