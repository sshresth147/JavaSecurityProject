package com.secure.notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // use this to configure web security at the HTTP level
public class SecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests

                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
/// adding two user as admin and user
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager();

        if (!manager.userExists("user1")) {  //Prevents duplicate user creation on restart
            manager.createUser(
                    User.withUsername("user1")
                            .password("{noop}password1") //Means no encoding
                            .roles("USER")
                            .build()
            );
        }

        if (!manager.userExists("admin")) {  //Prevents duplicate user creation on restart
            manager.createUser(
                    User.withUsername("admin")
                            .password("{noop}adminPass") //Means no encoding
                            .roles("ADMIN")
                            .build()
            );
        }

        return manager;
    }
}