package ru.gil.securitydemo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;
import static ru.gil.securitydemo.model.Permission.DEVELOPERS_READ;
import static ru.gil.securitydemo.model.Permission.DEVELOPERS_WRITE;
import static ru.gil.securitydemo.model.Role.ADMIN;
import static ru.gil.securitydemo.model.Role.USER;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChainUser(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((request) ->
                        request
//                                .requestMatchers(GET, "/api/**").hasAuthority(DEVELOPERS_READ.getPermission())
//                                .requestMatchers(POST, "/api/**").hasAuthority(DEVELOPERS_WRITE.getPermission())
//                                .requestMatchers(DELETE, "/api/**").hasAuthority(DEVELOPERS_WRITE.getPermission())
                                .requestMatchers("/*").permitAll()
                                .anyRequest()
                                .authenticated())
                .httpBasic();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .authorities(ADMIN.getAuthorities())
                        .build(),
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .authorities(USER.getAuthorities())
                        .build()
        );
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
