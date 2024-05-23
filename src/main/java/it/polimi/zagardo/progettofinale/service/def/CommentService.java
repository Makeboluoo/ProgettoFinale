package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsFromEvent(long idEvent);
}
