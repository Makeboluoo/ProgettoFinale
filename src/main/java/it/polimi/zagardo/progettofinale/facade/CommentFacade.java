package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.mapper.CommentMapper;
import it.polimi.zagardo.progettofinale.model.Comment;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.service.def.CommentService;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final EventService eventService;
    private final CommentMapper mapper;
    public AllEventCommentsDTO getEventComments(long idEvent) {
        List<Comment> c = commentService.getCommentsFromEvent(idEvent);
        return mapper.toAllEventCommentsDTO(c, idEvent);
    }

    public AllEventCommentsDTO postComment(long idEvent, String comment) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event e = eventService.findEventByID(idEvent);
        Comment singleComment = commentService.postComment(comment, userModel, e);
        List<Comment> c = commentService.getCommentsFromEvent(idEvent);
        return mapper.toAllEventCommentsDTO(c, idEvent);
    }
}
