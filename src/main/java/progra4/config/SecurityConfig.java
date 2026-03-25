package progra4.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/logout",
                                "/empresa/registro", "/oferente/registro",
                                "/puestos/buscar-por-caracteristicas",
                                "/oferentes/**",
                                "/css/**", "/static/**", "/uploads/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/empresa/**").hasAuthority("EMPRESA")
                        .requestMatchers("/oferente/**").hasAuthority("OFERENTE")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("usuario")
                        .passwordParameter("clave")
                        .successHandler((request, response, authentication) -> {
                            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
                            switch (user.getRol()) {
                                case "ADMIN" -> response.sendRedirect("/admin/dashboard");
                                case "EMPRESA" -> response.sendRedirect("/empresa/dashboard");
                                case "OFERENTE" -> response.sendRedirect("/oferente/dashboard");
                            }
                        })
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // sin hash por ahora, las claves están en texto plano
    }
}