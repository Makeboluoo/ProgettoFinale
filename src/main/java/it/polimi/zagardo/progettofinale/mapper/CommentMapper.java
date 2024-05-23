package it.polimi.zagardo.progettofinale.mapper;

import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.model.Comment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper {

    public CommentDTO toCommentDTO(Comment c){
        CommentDTO.Builder cDTO = new CommentDTO.Builder()
                .setId(c.getId())
                .setUsername(c.getUser().getUsername())
                .setText(c.getText());
        return cDTO.build();
    }

    public List<CommentDTO> toCommentDTO(List<Comment> c){
        return c.stream().map(this::toCommentDTO).toList();
    }
}
