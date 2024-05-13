package com.example.ATM_Backend.security;

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

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean //스프링에 의해 생성 또는 관리되는 객체 (ex: 컨트롤러, 서비스, 리포지터리)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http //인증되지 않은 모든 페이지의 요청을 허락
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .csrf((csrf) -> csrf // /h2-console/로 시작하는 모든 URL은 CSRF 검증을 하지 않는다
                        .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers((headers) -> headers //URL 요청 시 X-Frame-Options 헤더를 DENY 대신 SAMEORIGIN으로 설정하여 오류가 발생하지 않도록
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
                .formLogin((formLogin) -> formLogin //스프링 시큐리티의 로그인 설정을 담당
                        .loginPage("/user/login") //로그인 페이지의 URL은 /user/login
                        .defaultSuccessUrl("/")) //로그인 성공 시에 이동할 페이지는 루트 URL(/)
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")) // 로그아웃 URL을 /user/logout으로 설정
                        .logoutSuccessUrl("/") // 로그아웃이 성공하면 루트(/) 페이지로 이동
                        .invalidateHttpSession(true)) // .invalidateHttpSession(true)를 통해 로그아웃 시 생성된 사용자 세션도 삭제
        ;
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } //BCryptPasswordEncoder 클래스를 사용하여 암호화하여 비밀번호를 저장

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    } // AuthenticationManager는 스프링 시큐리티의 인증을 처리
    // 사용자 인증 시 앞에서 작성한 UserSecurityService와 PasswordEncoder를 내부적으로 사용하여 인증과 권한 부여 프로세스를 처리

}
