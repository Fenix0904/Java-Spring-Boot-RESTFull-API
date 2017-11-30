package ki.oprysko.config;

import ki.oprysko.web.LimitFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final LimitFilter filter;

    @Autowired
    public WebSecurityConfig(UserDetailsService userDetailsService, LimitFilter filter) {
        this.userDetailsService = userDetailsService;
        this.filter = filter;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers("/out").permitAll()
                .antMatchers("/all").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/apply").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/get-all-contracts").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/{userId}").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
                .antMatchers("/ban/{userId}").access("hasRole('ROLE_ADMIN')")
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
