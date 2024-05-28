package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PrivateEventDTO {
    //prendiamo il id dell'evento
    private long id;
    //prendiamo il titolo dell'evento
    private String title;
    //prendiamo la descrizione dell'evento
    private String description;
    //prendiamo la data dell'evento
    private LocalDateTime dateTime;
    //prendiamo il id del gruppo su cui è pubblicato l'evento
    private long idGroup;
    //prendiamo anche il nome di quel gruppo
    private String groupName;
    //prendiamo lo username del creatore
    private String creator;

    //step 1 il costruttore diventa privato


    private PrivateEventDTO(long id, String title, String description, LocalDateTime dateTime, long idGroup, String groupName, String creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.idGroup = idGroup;
        this.groupName = groupName;
        this.creator = creator;
    }

    //step 2 creo una inner class pubblica statica che copia tutti gli attributi della classe padre (no getter)
    public static class Builder{
        private long id;
        private String title;
        private String description;
        private LocalDateTime dateTime;
        private long idGroup;
        private String groupName;
        private String creator;

        public Builder setId(long id) {
            if(id<1) throw new DatoNonValidoException("id non valido");
            this.id = id;
            return this;
        }
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder setIdGroup(long idGroup) {
            this.idGroup = idGroup;
            return this;
        }

        public Builder setGroupName(String groupName) {
            this.groupName = groupName;
            return this;
        }

        public Builder setCreator(String creator){
            this.creator = creator;
            return this;
        }

        public PrivateEventDTO build(){
            if(id>0&&title!=null&&groupName!=null&&creator!=null)return new PrivateEventDTO(id,title,description,dateTime,idGroup,groupName,creator);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }

}
