package eu.ensup.compteservice.config;

import eu.ensup.compteservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordConfig passwordConfig;

    @Autowired
    private UserService userService;

//    @Autowired
//    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/**","browser/**","/login","/h2-console/**","/actuator/**").permitAll()
//            .antMatchers("/comptes/**").hasAuthority("ADMIN")
//            .antMatchers("/users/**").denyAll()
            .anyRequest().authenticated()
            .and()
//            .httpBasic() //Pop up
            .formLogin()
//            .loginPage("/login")
//            .defaultSuccessUrl("/", true)
//            .failureUrl("/login?error=true")
//            .permitAll()
//            .and()
//            .logout()
//            .logoutSuccessUrl("/")
//            .permitAll()
            .and()
            .csrf().disable();
        ;
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
//        userRepository.save(new User(1l,"test", Roles.USER,passwordConfig.passwordEncoder().encode("test")));

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordConfig.passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }


}

