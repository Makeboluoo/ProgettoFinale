package it.polimi.zagardo.progettofinale.security;

import it.polimi.zagardo.progettofinale.exception.UserNotFoundException;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final UserRepo userRepo;

    @Bean
    protected UserDetailsService getUserDetailsService(){
        return username -> userRepo.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected AuthenticationManager getAuthenticationManager(AuthenticationConfiguration contex) throws Exception {
        return contex.getAuthenticationManager();
    }

    @Bean
    protected AuthenticationProvider getProvider(){
        DaoAuthenticationProvider dap=new DaoAuthenticationProvider();
        dap.setPasswordEncoder(getPasswordEncoder());
        dap.setUserDetailsService(getUserDetailsService());
        return dap;
    }


}
