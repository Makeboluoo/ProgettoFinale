package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import it.polimi.zagardo.progettofinale.model.Comment;
import lombok.Getter;

@Getter
public class CommentDTO {
    //prendiamo l'id del commento
    private long id;
    //prendiamo lo username di chi ha postato il commento
    private String username;
    //prendiamo il testo del commento
    private String text;

    private CommentDTO(long id, String username, String text) {
        this.id = id;
        this.username = username;
        this.text = text;
    }

    public static class Builder{
        private long id;
        private String username;
        private String text;

        public Builder setId(long id){
            if(id<1) throw new DatoNonValidoException("id non valido");
            this.id = id;
            return this;
        }

        public Builder setUsername(String username){
            this.username = username;
            return this;
        }

        public Builder setText(String text){
            this.text = text;
            return this;
        }

        public CommentDTO build(){
            if(id>0&&username!=null&&text!=null) return new CommentDTO(id, username, text);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }
}
