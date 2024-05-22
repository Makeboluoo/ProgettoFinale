package it.polimi.zagardo.progettofinale.security;

import it.polimi.zagardo.progettofinale.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PathManager {

    private final AuthenticationProvider provider;
    private final UserFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                                .requestMatchers("/user/login").permitAll()
                                .requestMatchers("user/register").permitAll()
                                .requestMatchers("user/home").permitAll()
                                .requestMatchers("user/profile").permitAll()
                                .requestMatchers("group/groups").permitAll()
                                .requestMatchers("group/creation").permitAll()
                                .requestMatchers("group/search").permitAll()
                                .requestMatchers("group/join").permitAll()
                                .requestMatchers("group/delete").hasRole(Role.Administrator.name())
                                .requestMatchers("event/creation").permitAll()
                                .requestMatchers("event/allEvents").permitAll()
                                .requestMatchers("event/myEvents").permitAll()
                                .requestMatchers("event/singleEvent").permitAll()
                                .requestMatchers("event/participate").permitAll()
                                .requestMatchers("event/resign").permitAll()
                                .requestMatchers("event/delete").hasRole(Role.Administrator.name())
                                .requestMatchers("event/findAll").authenticated()
                        )
                .authenticationProvider(provider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
