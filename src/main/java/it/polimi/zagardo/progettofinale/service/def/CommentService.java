package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.*;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsFromEvent(long idEvent);

    Comment postComment(String comment, GroupRights groupRights, Event e);
}
