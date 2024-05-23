package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.mapper.CommentMapper;
import it.polimi.zagardo.progettofinale.model.Comment;
import it.polimi.zagardo.progettofinale.service.def.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentFacade {

    private final CommentService commentService;
    private final CommentMapper mapper;
    public List<CommentDTO> getEventComments(long idEvent) {
        List<Comment> c = commentService.getCommentsFromEvent(idEvent);
        return mapper.toCommentDTO(c);
    }
}
