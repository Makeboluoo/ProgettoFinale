package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.facade.CommentFacade;
import it.polimi.zagardo.progettofinale.service.def.CommentService;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentFacade commentFacade;

    /*Manda alla pagina html event_comments tutti i commenti di un evento con id = id_event
    passato dalla pagina event.html o single_event.html*/
    @PostMapping(path = "/eventComments")
    public String eventComments(@RequestParam("id_event") long idEvent, Model model){
        //ritorna la lista dei commenti di un determinato evento con id = id_event
        AllEventCommentsDTO comments = commentFacade.getEventComments(idEvent);
        model.addAttribute("comments", comments);
        return "comments/event_comments";
    }

    /*Ritorna alla pagina html event_comments la lista aggiornata dei commenti dopo che l'utente
    ha postato un commento nella pagina html event_comments con il parametro "comment" ovvero il testo del commento*/
    @PostMapping(path = "/postComment")
    public String postComment(@RequestParam("id_event") long idEvent, @RequestParam("comment") String comment, Model model){
        //Posta un commento e ritorna la lista aggiornata di commenti di un evento.
        AllEventCommentsDTO comments = commentFacade.postComment(idEvent, comment);
        model.addAttribute("comments", comments);
        return "comments/event_comments";
    }

}

