package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
