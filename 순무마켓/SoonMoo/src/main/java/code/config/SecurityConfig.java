package code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import code.service.UserService;

@Configuration
@EnableWebSecurity 
public class SecurityConfig {

    private final UserService userService;


    public SecurityConfig(@Lazy UserService u)
    {
        userService = u;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    
        return configuration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //csrf disable
        http
                .csrf((auth) -> auth.disable());
        //HTTP Basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        http
                .formLogin(f -> f
                                .loginPage("/login")
                                .loginProcessingUrl("/login/loginProcess")
                                .usernameParameter("userId")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                );
        // 로그아웃 경로
        http
                .logout( logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/")

                );

        // oauth2
	http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/login")
                        .userInfoEndpoint((userInfoEndpointConfig) -> userInfoEndpointConfig
                        .userService(userService))  
                );
        
        //경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/").permitAll()
                        .anyRequest().permitAll());


        return http.build();
    }


}
