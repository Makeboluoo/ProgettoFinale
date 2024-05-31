package it.polimi.zagardo.progettofinale.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    //todo Crea un handler per ogni eccezione oppure un handler che catcha tutto?
    @ExceptionHandler({GroupRightsNotFoundException.class})
    public String groupRightsNotFound(){
        return "errorPage";
    }
}
