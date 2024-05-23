package pl.coderslab;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = false)
public class SecurityConfig {


    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        http.csrf(withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/user/**").hasAnyRole("USER")
//                        )
                .formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/"))
                .logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout")))
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/403"))
                .httpBasic(withDefaults());
        return http.build();
    }

//    private final DataSource dataSource;
//
//    public SecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder managerBuilder) throws Exception {
//        managerBuilder.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
//                .authoritiesByUsernameQuery("""
//                        select username, authority
//                        from users_authorities ua
//                                 join users u on u.id = ua.user_id
//                                 join authorities a on a.id = ua.authorities_id  where u.username = ?;
//                        """);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
