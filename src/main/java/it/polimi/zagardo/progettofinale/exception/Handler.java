package it.polimi.zagardo.progettofinale.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {


    @ExceptionHandler({DatoNonValidoException.class})
    public String datoNonValido(Model model, DatoNonValidoException ex){
        model.addAttribute("error", ex.getMessage());
        return "templates/Exceptions/dati_non_validi.html";
    }

    @ExceptionHandler({UserNotFoundException.class})
    public String datoNonValido(Model model, UserNotFoundException ex){
        model.addAttribute("error", "nessun utente con queste credenziali");
        return "templates/Exceptions/user_not_in_session.html";
    }
}
