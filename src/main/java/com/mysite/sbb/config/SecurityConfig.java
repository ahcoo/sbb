package com.mysite.sbb.config;

import com.mysite.sbb.siteuser.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration //스프링의 환경설정 파일임을 의미하는 애너테이션이다. 여기서는 스프링 시큐리티의 설정을 위해 사용
@EnableWebSecurity //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserSecurityService userSecurityService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                .permitAll()
                .and()
                    .csrf()
                    .ignoringAntMatchers("/h2-console/**")
                .and()
                    .headers()
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(
                            XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                    .formLogin()
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}

//331f5aac-b8a3-4947-9c5d-e4f6b9505aeb