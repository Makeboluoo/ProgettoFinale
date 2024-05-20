package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserDTO {
    private long id;
    private String username;
    private List<GroupRightsDTO> groupRightsDTO;

    private UserDTO(long id, String username, List<GroupRightsDTO> groupRightsDTO) {
        this.id = id;
        this.username = username;
        this.groupRightsDTO = groupRightsDTO;
    }

    public static class Builder{
        private long id;
        private String username;
        private List<GroupRightsDTO> groupRightsDTO;

        public Builder setId(long id) {
            this.id = id;
            return this;
        }
        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }
        public Builder setGroupRightsDTO(List<GroupRightsDTO> groupRightsDTOS){
        this.groupRightsDTO=groupRightsDTOS;
        return this;
        }

        public UserDTO build(){
            if(id>0&&username!=null)return new UserDTO(id,username,groupRightsDTO);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }

}
