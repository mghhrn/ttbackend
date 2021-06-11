package ir.mghhrn.ttbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import ir.mghhrn.ttbackend.Repository.UserRepository;
import ir.mghhrn.ttbackend.security.RestAuthenticationEntryPoint;
import ir.mghhrn.ttbackend.security.TokenAuthenticationFilter;
import ir.mghhrn.ttbackend.security.TokenHelper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                    .antMatchers("/api/v1/therapy-session/**")
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, userRepository, objectMapper, messageSource), BasicAuthenticationFilter.class)
                .authorizeRequests()
                    .anyRequest()
                        .authenticated()
                .and()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                    .disable()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint);
    }
}
