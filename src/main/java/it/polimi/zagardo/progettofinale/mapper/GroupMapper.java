package it.polimi.zagardo.progettofinale.mapper;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {

    public GroupDTO toGroupDTO(GroupModel g){
        GroupDTO.Builder gDTO=new GroupDTO.Builder()
                .setId(g.getId())
                .setName(g.getName());
        return gDTO.build();
    }

    public List<GroupDTO> toGroupDTO(List<GroupModel> g){
        return g.stream().map(this::toGroupDTO).toList();
    }

    public SingleGroupDTO toSingleGroupDTO(GroupModel g){
        List<GroupRightsDTO> groupRightsDTOS = new ArrayList<>();
        for (GroupRights groupRight: g.getGroupRights()) {
            GroupRightsDTO grDTO = new GroupRightsDTO.Builder()
                    .setId(groupRight.getId()).setGroupName(groupRight.getGroup().getName()).setRole(groupRight.getRole()).setUsername(groupRight.getUser().getUsername())
                    .build();
            groupRightsDTOS.add(grDTO);
        }
        SingleGroupDTO.Builder gDTO=new SingleGroupDTO.Builder()
                .setID(g.getId())
                .setName(g.getName())
                .setCreationDateTime(g.getCreationDay())
                .setGroupRightsDTOS(groupRightsDTOS);

        return gDTO.build();
    }

}
