package it.polimi.zagardo.progettofinale.dtoTest;

import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import it.polimi.zagardo.progettofinale.mapper.CommentMapper;
import it.polimi.zagardo.progettofinale.model.Comment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class commentdtoTest {
    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void testIDNotValid() {

        assertThrows(DatoNonValidoException.class, () -> {
            CommentDTO comment = new CommentDTO.Builder()
                    .setId(0)
                    .setUsername("username")
                    .setText("text")
                    .build();
        });
    }
    @Test
    public void testAllNotValid() {
        assertThrows(DatoNonValidoException.class, () -> {
            CommentDTO comment = new CommentDTO.Builder()
                    .setId(1)
                    .setUsername(null) // This should trigger the exception
                    .setText(null) // This should trigger the exception
                    .build();
        });
    }
}
