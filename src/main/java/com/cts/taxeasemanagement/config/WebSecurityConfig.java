package com.cts.taxeasemanagement.config;

import com.cts.taxeasemanagement.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final HandlerExceptionResolver resolver;

    public WebSecurityConfig(JwtAuthFilter jwtAuthFilter,
                             @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.resolver = resolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, e) ->
                                resolver.resolveException(request, response, null, e))
                        .accessDeniedHandler((request, response, e) ->
                                resolver.resolveException(request, response, null, e))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/filings/submit").hasRole("TAXPAYER")
                        .requestMatchers("/api/taxpayers/**").hasRole("TAXPAYER")
                        .requestMatchers("/api/filings/taxpayer/**").hasAnyRole("TAXPAYER", "OFFICER")
                        .requestMatchers("/api/filings/*/status").hasRole("OFFICER")
                        .requestMatchers("/api/documents/upload").hasRole("TAXPAYER")
                        .requestMatchers("/api/documents/filing/**").hasAnyRole("TAXPAYER", "OFFICER")
                        .requestMatchers("/api/payments/pay", "/api/payments/retry/**").hasRole("TAXPAYER")
                        .requestMatchers("/api/payments/history/**").hasAnyRole("TAXPAYER", "OFFICER")
                        .requestMatchers("/api/compliance/**").hasAnyRole("COMPLIANCE", "MANAGER", "ADMIN", "TAXPAYER")
                        .requestMatchers("/api/compliance/taxpayer/**").hasAnyRole("TAXPAYER", "COMPLIANCE")
                        .requestMatchers("/api/audit/**").hasRole("AUDITOR")
                        .requestMatchers("/api/reports/payments/**", "/api/reports/revenue/**").hasAnyRole("MANAGER", "AUDITOR")
                        .requestMatchers("/api/reports/audits/**").hasAnyRole("AUDITOR", "ADMINISTRATOR")
                        .requestMatchers("/api/reports/custom/download").hasAnyRole("ADMINISTRATOR", "MANAGER")
                        .requestMatchers("/api/notifications/broadcast").hasAnyRole("ADMINISTRATOR", "MANAGER")
                        .requestMatchers("/api/notifications/user/**").authenticated()
                        .requestMatchers("/api/audit-logs/**").hasRole("ADMINISTRATOR")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
