package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findByEvent_Id(long idEvent);
    //ritorna la lista di commenti di un determinato evento

}
