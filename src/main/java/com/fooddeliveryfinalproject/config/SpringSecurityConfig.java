package com.fooddeliveryfinalproject.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {

    private final UserDetailsService userDetailsService;

    private final JWTRequestFilter jwtRequestFilter;

    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    public SpringSecurityConfig(UserDetailsService userDetailsService,
                                JWTRequestFilter jwtRequestFilter,
                                AuthenticationConfiguration authenticationConfiguration) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.authenticationConfiguration = authenticationConfiguration;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);  // Шифрование паролей
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/customers/register", "/login", "/logout").permitAll()
                        .requestMatchers("/admins/register").permitAll()
                        .requestMatchers("/managers/register").permitAll()
                        .requestMatchers("/drivers/register").permitAll()
                        .requestMatchers("/restaurant/list", "/restaurant/search", "/restaurant/{id}",
                                "/restaurant/average-rating/{restaurantId}", "/restaurant/all-reviews/{restaurantId}").permitAll()
                        .requestMatchers("/restaurant-branch/menu/{restaurantBranchId}", "/restaurant-branch/list/{restaurantId}",
                        "/restaurant-branch/{id}", "/restaurant-branch/categories/{branchId}",
                                "/restaurant-branch/categories-with-menu-items/{branchId}", "/restaurant-branch/{branchId}/category/{categoryId}").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).clearAuthentication(true))
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
