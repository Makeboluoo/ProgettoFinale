package it.polimi.zagardo.progettofinale.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Handler {

    //todo Eliminare tutte le exception che non uso
    @ExceptionHandler({GroupRightsNotFoundException.class})
    public String groupRightsNotFound(){
        return "errorPage";
    }
}
