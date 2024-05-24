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
        List<Comment> c = commentService.getCommentsFromEvent(idEvent);
        return mapper.toAllEventCommentsDTO(c, idEvent);
    }

    public AllEventCommentsDTO postComment(long idEvent, String comment) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event e = eventService.findEventByID(idEvent);
        GroupRights groupRights = groupRightsService.searchGroupRightByIds(userModel.getId(), e.getCreatorGR().getGroup().getId());
        Comment singleComment = commentService.postComment(comment, groupRights, e);
        List<Comment> c = commentService.getCommentsFromEvent(idEvent);
        return mapper.toAllEventCommentsDTO(c, idEvent);
    }
}
