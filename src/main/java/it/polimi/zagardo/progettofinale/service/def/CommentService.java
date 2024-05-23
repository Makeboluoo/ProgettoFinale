package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.Comment;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsFromEvent(long idEvent);

    Comment postComment(String comment, UserModel userModel, Event e);
}
