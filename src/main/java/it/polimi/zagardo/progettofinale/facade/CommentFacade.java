package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.mapper.CommentMapper;
import it.polimi.zagardo.progettofinale.model.*;
import it.polimi.zagardo.progettofinale.service.def.CommentService;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final EventService eventService;
    private final GroupRightsService groupRightsService;
    private final CommentMapper mapper;
    public AllEventCommentsDTO getEventComments(long idEvent) {
        //Prende la lista di commenti di un determinato evento con id = id_event e trasforma gli oggetti in DTO
        List<Comment> c = commentService.getCommentsFromEvent(idEvent);
        return mapper.toAllEventCommentsDTO(c, idEvent);
    }

    public AllEventCommentsDTO postComment(long idEvent, String comment) {
        //prendi lo userModel della sessione corrente
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //cerca un evento con un determinato id = id_event
        Event e = eventService.findEventByID(idEvent);
        //cerca il groupRight relativo all'utente in sessione (userModel) e l'evento su cui sta postando un commento (e)
        GroupRights groupRights = groupRightsService.searchGroupRightByIds(userModel.getId(), e.getCreatorGR().getGroup().getId());
        //Posta il commento dell'utente
        Comment singleComment = commentService.postComment(comment, groupRights, e);
        //Ritorna la lista di commenti aggiornata e la converte in lista di DTO
        List<Comment> c = commentService.getCommentsFromEvent(idEvent);
        return mapper.toAllEventCommentsDTO(c, idEvent);
    }
}
