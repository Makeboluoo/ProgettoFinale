package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.model.Comment;
import it.polimi.zagardo.progettofinale.repository.CommentRepo;
import it.polimi.zagardo.progettofinale.service.def.CommentService;
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
        return commentRepo.findByEvent_Id(idEvent);
    }
}
