package it.polimi.zagardo.progettofinale.commentfacade;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.facade.CommentFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PostComments {
    @Autowired
    private CommentFacade commentFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testCommentoConTuttoOK() {
        AllEventCommentsDTO comment= commentFacade.getEventComments(9);
        List<CommentDTO> c=comment.getCommentsDTO();
        String text = "Commento 1";
        UserModel userModel = userRepo.findById(1L).orElse(null);
        AllEventCommentsDTO allEventCommentsDTO = commentFacade.postComment(9, text, userModel);
        assertNotNull(allEventCommentsDTO);
        assertFalse(allEventCommentsDTO.getCommentsDTO().isEmpty());
        assertThat(allEventCommentsDTO.getCommentsDTO().size()).isEqualTo(c.size()+1);
        assertThat(allEventCommentsDTO.getCommentsDTO().get(allEventCommentsDTO.getCommentsDTO().size()-1).getText().equals(text));
    }
}
