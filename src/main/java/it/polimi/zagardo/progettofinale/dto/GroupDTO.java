package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import lombok.Getter;

@Getter
public class GroupDTO {
    private long id;
    private String name;
    //todo: vogliamo anche aggiungere il numero di partecipanti nella scermata di tutti i gruppi? Nome + membri?


    private GroupDTO(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static class Builder {
        private long id;
        private String name;

        public Builder setId(long id) {
            if(id<1) throw new DatoNonValidoException("id non valido");
            this.id = id;
            return this;
        }

        public Builder setName(String name){
            this.name=name;
            return this;
        }

        public GroupDTO build(){
            if(id>0&&name!=null)return new GroupDTO(id,name);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }
}