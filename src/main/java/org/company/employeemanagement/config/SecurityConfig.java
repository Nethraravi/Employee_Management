package org.company.employeemanagement.config;

import org.company.employeemanagement.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(withDefaults()).csrf(csrf -> csrf.disable()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers("/auth/change-password").authenticated()
                .requestMatchers(HttpMethod.GET, "/dashboard")
                        .hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET,"/employees/**").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.POST,"/employees/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/employees/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/employees/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/departments/**").hasAnyRole("ADMIN","USER")
                .requestMatchers(HttpMethod.POST, "/departments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/departments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/departments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/leaves")
                .hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/leaves/my")
                .hasRole("USER")

                .requestMatchers(HttpMethod.GET, "/leaves")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/leaves/employee/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/leaves/status/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/leaves/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.PUT, "/leaves/**")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.DELETE, "/leaves/**")
                .hasRole("ADMIN"))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
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
    public org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource() {
        org.springframework.web.cors.CorsConfiguration configuration = new org.springframework.web.cors.CorsConfiguration();
        configuration.setAllowedOrigins(java.util.List.of("http://localhost:4200"));
        configuration.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(java.util.List.of("*"));
        org.springframework.web.cors.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;

    }
}
