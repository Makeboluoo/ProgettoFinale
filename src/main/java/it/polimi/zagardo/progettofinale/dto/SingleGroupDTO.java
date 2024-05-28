package it.polimi.zagardo.progettofinale.dto;

import it.polimi.zagardo.progettofinale.exception.DatoNonValidoException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SingleGroupDTO {
    //prendiamo il id del gruppo
    private long id;
    //prendiamo il nome del gruppo
    private String name;
    //prendiamo la data di creazione
    private LocalDateTime creationDateTime;
    //prendiamo la lista di groupRight (ovvero membri)
    private List<GroupRightsDTO> groupRightsDTOS;

    private SingleGroupDTO(long id, String name, LocalDateTime creationDateTime, List<GroupRightsDTO> groupRightsDTOS) {
        this.id = id;
        this.name = name;
        this.creationDateTime = creationDateTime;
        this.groupRightsDTOS = groupRightsDTOS;
    }

    public static class Builder{
        private long id;
        private String name;
        private LocalDateTime creationDateTime;
        private List<GroupRightsDTO> groupRightsDTOS;

        public Builder setID(long id){
            this.id = id;
            return this;
        }
        public Builder setName(String name){
            this.name = name;
            return this;
        }
        public Builder setCreationDateTime(LocalDateTime creationDateTime){
            this.creationDateTime = creationDateTime;
            return this;
        }
        public Builder setGroupRightsDTOS(List<GroupRightsDTO> groupRightsDTOS){
            this.groupRightsDTOS = groupRightsDTOS;
            return this;
        }

        public SingleGroupDTO build(){
            if(id>0&&name!=null&&creationDateTime!=null&&!groupRightsDTOS.isEmpty())return new SingleGroupDTO(id,name,creationDateTime,groupRightsDTOS);
            else throw new DatoNonValidoException("non tutti i dati sono accettabili");
        }
    }
}
