package it.polimi.zagardo.progettofinale.commentfacade;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.facade.CommentFacade;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest
public class GetEventComments {

    @Autowired
    private CommentFacade commentFacade;

    @Test
    public void TestIdGiusto(){

        //todo cambia ogni volta il numero
        AllEventCommentsDTO comment= commentFacade.getEventComments(9);
        List<CommentDTO> c=comment.getCommentsDTO();
        assertEquals(c.size(),15);
    }

    @Test
    public void TestIdErrato(){
        AllEventCommentsDTO comment= commentFacade.getEventComments(54);
        List<CommentDTO> c=comment.getCommentsDTO();
        assertThat(comment.getCommentsDTO().size()).isEqualTo(0);
    }


}
