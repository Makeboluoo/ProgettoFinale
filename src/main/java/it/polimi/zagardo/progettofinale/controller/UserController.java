package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.UserDTO;
import it.polimi.zagardo.progettofinale.facade.UserFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

    private final UserFacade userFacade;

    //porta alla pagina di login
    @GetMapping(path = "/login")
    public String loginPage(){
        return "register_login_logout_profile/login_page";
    }

    //esegue il logout dell'utente
    @GetMapping(path = "/logout")
    public String logoutPage(HttpSession session){
        session.invalidate();
        return "register_login_logout_profile/logout_page";
    }

    //viene gestito il login dalla pagina html login_page
    @PostMapping(path = "/login")
    public String loginCheck(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
        //viene fatto il login
        if (userFacade.loginCheck(username, password)){
            //viene settato lo username dell'utente nella sessione
            session.setAttribute("username", username);
            return "redirect:/user/home";
        }
        else return "register_login_logout_profile/login_failed";
    }

    //si viene diretti alla pagina di registrazione
    @GetMapping(path = "/register")
    public String register(){
        return "register_login_logout_profile/register";
    }

    //viene gestita la registrazione di un nuovo utente
    @PostMapping(path = "/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
        //si controlla che l'utente non esita già
        if (!userFacade.loginCheck(username, password)){
            //viene creato un nuovo account
            userFacade.createAccount(username,password);
            //viene settato lo username dell'utente nella sessione
            session.setAttribute("username", username);
            return "redirect:/user/home";
            }
        else {
            return "register_login_logout_profile/registration_failed";
        }
    }

    //si indirizza alla home page
    @GetMapping(path = "/home")
    public String homePage(){
        return "home/home";
    }

    //si indirizza alla pagina di profilo
    @GetMapping(path = "/profile")
    public String profile(Model model){
        //si prende lo user dalla sessione e lo si converte in DTO
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO user = userFacade.getProfile(userModel);
        model.addAttribute("user", user);
        return "register_login_logout_profile/profile";

    }

}
