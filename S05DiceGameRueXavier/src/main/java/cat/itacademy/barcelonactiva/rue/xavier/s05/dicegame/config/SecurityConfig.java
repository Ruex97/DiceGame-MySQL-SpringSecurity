package cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.config;

import cat.itacademy.barcelonactiva.rue.xavier.s05.dicegame.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * The SecurityFilterChain is responsible for filtering incoming requests, performing authentication and authorization,
     * and controlling access to protected resources.
     * @param httpSecurity used to configure the security settings
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests()                       // Decide whitelist(endpoints not required to be authenticated
                .requestMatchers("/player/auth/**")                    // Whitelist
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()                                          // Configuration of session management. Stateless session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()                                          // Authentication provider
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter before the UsernamePasswordAuthenticationFilter.
        return httpSecurity.build();
    }
}
