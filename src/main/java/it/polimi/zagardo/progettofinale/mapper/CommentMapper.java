package it.polimi.zagardo.progettofinale.mapper;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.model.Comment;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

//    public CommentDTO toCommentDTO(Comment c){
//        CommentDTO.Builder cDTO = new CommentDTO.Builder()
//                .setId(c.getId())
//                .setUsername(c.getGroupRights().getUser().getUsername())
//                .setText(c.getText());
//        return cDTO.build();
//    }
//
//    public List<CommentDTO> toCommentDTO(List<Comment> c, long idEvent){
//        return c.stream().map(this::toCommentDTO).toList();
//    }

    public AllEventCommentsDTO toAllEventCommentsDTO(List<Comment> c, long idEvent){
        List<CommentDTO> comments = new ArrayList<>();
        for(Comment singleComment: c){
            CommentDTO cDTO = new CommentDTO.Builder()
                    .setId(singleComment.getId())
                    .setUsername(singleComment.getGroupRights().getUser().getUsername())
                    .setText(singleComment.getText())
                    .build();
            comments.add(cDTO);
        }
        AllEventCommentsDTO.Builder aecDTO = new AllEventCommentsDTO.Builder()
                .setIdEvent(idEvent)
                .setCommentsDTO(comments);
        return aecDTO.build();
    }
}
