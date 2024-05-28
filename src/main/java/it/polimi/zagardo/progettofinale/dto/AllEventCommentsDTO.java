package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import lombok.Getter;

import java.util.List;

@Getter
public class AllEventCommentsDTO {
    //prendiamo il id dell'evento
    private long idEvent;
    //prendiamo la lista di commenti convertiti in DTO relativi all'evento
    private List<CommentDTO> commentsDTO;

    private AllEventCommentsDTO(long idEvent, List<CommentDTO> commentsDTO) {
        this.idEvent = idEvent;
        this.commentsDTO = commentsDTO;
    }

    public static  class Builder{
        private long idEvent;
        private List<CommentDTO> commentsDTO;

        public Builder setIdEvent(long idEvent){
            this.idEvent = idEvent;
            return this;
        }

        public Builder setCommentsDTO(List<CommentDTO> commentsDTO){
            this.commentsDTO = commentsDTO;
            return this;
        }

        public AllEventCommentsDTO build(){
            if(idEvent>0)return new AllEventCommentsDTO(idEvent, commentsDTO);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }
}
