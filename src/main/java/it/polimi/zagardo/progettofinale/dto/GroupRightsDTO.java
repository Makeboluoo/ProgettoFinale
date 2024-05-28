package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import lombok.Getter;

@Getter
public class GroupRightsDTO {
    //prendiamo il id del group right
    private long id;
    //prendiamo il nome del gruppo relativo
    private String groupName;
    //prendiamo il ruolo del group right
    private Role role;
    //prendiamo lo username dell'utente relativo
    private String username;

    private GroupRightsDTO(long id, String groupName, Role role, String username) {
        this.id = id;
        this.groupName = groupName;
        this.role = role;
        this.username = username;
    }

    public static class Builder{
        private long id;
        private String groupName;
        private Role role;
        private String username;


        public Builder setId(long id){
            //fai controlli
            this.id = id;
            return this;
        }
        public Builder setGroupName(String groupName){
            //fai controlli
            this.groupName = groupName;
            return this;
        }
        public Builder setRole(Role role){
            //fai controlli
            this.role = role;
            return this;
        }

        public Builder setUsername(String username){
            //fai i controlli
            this.username=username;
            return this;
        }

        public GroupRightsDTO build(){
            if(id>0&&groupName!=null&&role!=null&&username!=null)return new GroupRightsDTO(id,groupName,role,username);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }
}
