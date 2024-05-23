package it.polimi.zagardo.progettofinale.mapper;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import org.springframework.stereotype.Component;

@Component
public class GroupRightsMapper {
    public GroupRightsDTO toGroupRightDTO(GroupRights gr){
        GroupRightsDTO.Builder grDTO=new GroupRightsDTO.Builder()
                .setId(gr.getId())
                .setGroupName(gr.getGroup().getName())
                .setRole(gr.getRole())
                .setUsername(gr.getUser().getUsername());
        return grDTO.build();
    }
}
