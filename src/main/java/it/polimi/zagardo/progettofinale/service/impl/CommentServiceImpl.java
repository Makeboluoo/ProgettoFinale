package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.model.Comment;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.CommentRepo;
import it.polimi.zagardo.progettofinale.service.def.CommentService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private final CommentRepo commentRepo;

    @Override
    public List<Comment> getCommentsFromEvent(long idEvent) {
        //ritorna la lista di commenti di un determinato evento
        return commentRepo.findByEvent_Id(idEvent);
    }

    @Transactional
    @Override
    public Comment postComment(String comment, GroupRights groupRights, Event event) {
        //Crea un commento per un determinato evento con i vari attributi passati e lo salva sul database
        Comment c = new Comment(comment,groupRights,event);
        commentRepo.save(c);
        return c;
    }
}
