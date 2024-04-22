package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class PawUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = us.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user name " + username));

        // TODO :
        List<GrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("ROLE_EDITOR"),
                new SimpleGrantedAuthority("ROLE_VIEWER"));
        return new PawUserDetails(user.getUsername(), user.getPassword(), roles);
    }
}
