package hyfz.hyfz.Security;

import hyfz.hyfz.config.JWTAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityFilterChainConfig {

    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;


    public SecurityFilterChainConfig(AuthenticationEntryPoint authenticationEntryPoint, JWTAuthFilter jwtAuthFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(
                        (auth) ->
                                auth
                                        .requestMatchers("/user/paginate").authenticated()
                                        .anyRequest().permitAll()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(e-> e.authenticationEntryPoint(authenticationEntryPoint));

        return http.build();
    }


}
