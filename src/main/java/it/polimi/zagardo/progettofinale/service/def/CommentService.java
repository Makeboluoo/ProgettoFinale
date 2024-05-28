package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.*;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsFromEvent(long idEvent);
    //ritorna la lista di commenti di un determinato evento

    Comment postComment(String comment, GroupRights groupRights, Event e);
    //posta un commento per un determinato evento
}
