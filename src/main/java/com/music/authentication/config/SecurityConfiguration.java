package com.music.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfiguration {

    public static final String ADMIN = "ADMIN";
    private final SecurityFilter securityFilter;

    public SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity
                 .csrf(csrf -> csrf.ignoringRequestMatchers(antMatcher("/h2-console/**")).disable())
                 .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                         .requestMatchers(antMatcher("/v1/music/auth/register")).permitAll()
                         .requestMatchers(antMatcher("/v1/music/auth/login")).permitAll()
                         .requestMatchers(antMatcher("/v1/music/genders/post")).hasRole(ADMIN)
                         .requestMatchers(antMatcher("/v1/music/genders/put/{cdGender}")).hasRole(ADMIN)
                         .requestMatchers(antMatcher("/v1/music/genders/delete/{cdGender}")).hasRole(ADMIN)
                         .requestMatchers(antMatcher("/v1/music/musics/post")).hasRole(ADMIN)
                         .requestMatchers(antMatcher("/v1/music/musics/put/{cdGender}")).hasRole(ADMIN)
                         .requestMatchers(antMatcher("/v1/music/musics/delete/{cdGender}")).hasRole(ADMIN)
                        .anyRequest().authenticated())
                 .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                 .headers(headers -> headers.frameOptions().disable());

        return httpSecurity.build();
    }
}
