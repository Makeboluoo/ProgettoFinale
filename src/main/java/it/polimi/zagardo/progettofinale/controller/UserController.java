package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.UserDTO;
import it.polimi.zagardo.progettofinale.facade.UserFacade;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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

    //todo c'è un problema con l'accesso: non mi fa il controllo della sessione.
    @GetMapping(path = "/login")
    public String loginPage(){
        return "register_login_logout_profile/login_page";
    }

    //todo come gestire il logout??
//    @GetMapping(path = "/logout")
//    public String logoutPage(HttpSession session){
//        session.invalidate();
//        return "logout_page";
//    }

    @PostMapping(path = "/login")
    public String loginCheck(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
        if (userFacade.loginCheck(username, password)){
            session.setAttribute("username", username);
            return "redirect:/user/home";
        }
        else return "register_login_logout_profile/login_failed";
    }

    @GetMapping(path = "/register")
    public String register(){
        return "register_login_logout_profile/register";
    }

    @PostMapping(path = "/register")
    public String register(@RequestParam("username") String username, @RequestParam("password") String password){
        if (!userFacade.loginCheck(username, password)){
            userFacade.createAccount(username,password);
            return "redirect:/user/home";
            }
        else {
            //todo io qui sto mandando ad una pagina di errore, va bene o devo creare le exception?
            return "register_login_logout_profile/registration_failed";
        }
    }

    @GetMapping(path = "/home")
    public String homePage(){
        return "home/home";
    }

    @GetMapping(path = "/profile")
    public String profile(Model model){
        UserDTO user = userFacade.getProfile();
        model.addAttribute("user", user);
        return "register_login_logout_profile/profile";

    }

}
