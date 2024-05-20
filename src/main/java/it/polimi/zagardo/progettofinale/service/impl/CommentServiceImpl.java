package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.repository.CommentRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl {
    @Autowired
    private final CommentRepo commentRepo;

}
