package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SingleEventDTO {
    private long id;
    private String title;
    private String description;
    private LocalDateTime dateTime;
    private long idGroup;
    private String groupName;
    private String creator;
    private long idUser;
    private Role role;

    //step 1 il costruttore diventa privato


    private SingleEventDTO(long id, String title, String description, LocalDateTime dateTime, long idGroup, String groupName, String creator, long idUser, Role role) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.idGroup = idGroup;
        this.groupName = groupName;
        this.creator = creator;
        this.idUser = idUser;
        this.role = role;
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
        private long idUser;
        private Role role;

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

        public Builder setIdUser(long idUser){
            this.idUser = idUser;
            return this;
        }

        public Builder setRole(Role role){
            this.role = role;
            return this;
        }

        public SingleEventDTO build(){
            if(id>0&&title!=null&&groupName!=null&&creator!=null)return new SingleEventDTO(id,title,description,dateTime,idGroup,groupName,creator ,idUser, role);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }

}
