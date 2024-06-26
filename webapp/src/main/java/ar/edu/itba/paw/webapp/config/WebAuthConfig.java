package ar.edu.itba.paw.webapp.config;

import ar.edu.itba.paw.webapp.auth.PawUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

@EnableWebSecurity
@ComponentScan({ "ar.edu.itba.paw.webapp.auth" })
@Configuration
public class WebAuthConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PawUserDetailsService userDetailsService;

    @Value("classpath:rememberMe.key")
    private Resource rememberMeKey;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .invalidSessionUrl("/login")
            .and().authorizeHttpRequests()
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/login", "/", "/create").anonymous()
                .requestMatchers("/myprofile").hasAnyRole("USER")
                .requestMatchers("/post/review").hasAnyRole("EDITOR")
                 // .requestMatchers("/post/edit").access("@AccessHelper.canEdit") SS Expression Language
                 // requestMatchers("/post/edit").hasAuthority("$u.id = $userId") ??
                .anyRequest().authenticated()
            .and().formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .defaultSuccessUrl("/", false)
                .failureUrl("/login?error")
                .loginProcessingUrl("/login")
            .and().rememberMe()
                .rememberMeParameter("rememberme")
                .userDetailsService(userDetailsService)
                // .rememberMeCookieName()
                .key("remember-me-key-change-this-in-prod-with-openssl")
                // .key(FileCopyUtils.copyToString(new InputStreamReader(rememberMeKey.getInputStream())))
                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(30))
            .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            .and().exceptionHandling()
                .accessDeniedPage("/403")
            .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers("/css/**", "/js/**", "/img/**", "/logo/**", "favicon.ico");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
