package it.polimi.zagardo.progettofinale.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserFilter extends OncePerRequestFilter {

    private final UserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");

        String loginUrl = request.getContextPath() + "/user/login"; // URL della pagina di login
        String registerUrl = request.getContextPath() + "/user/register"; // URL della pagina di registrazione

        // Controllo se la richiesta corrente è per la pagina di login
        if (request.getRequestURI().equals(loginUrl) || request.getRequestURI().equals(registerUrl)) {
            filterChain.doFilter(request,response); // Se è la richiesta di login, passa alla pagina di login
            return;
        }

        if(username==null|| SecurityContextHolder.getContext().getAuthentication()!=null){
//            filterChain.doFilter(request,response);
            response.sendRedirect(loginUrl);
        }else{
            UserDetails user=service.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken upat=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(upat);
            filterChain.doFilter(request,response);
        }
    }
}
