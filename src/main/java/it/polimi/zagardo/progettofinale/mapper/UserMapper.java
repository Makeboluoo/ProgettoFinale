package it.polimi.zagardo.progettofinale.mapper;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.UserDTO;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    public UserDTO toUserDTO(UserModel userModel){
        List<GroupRightsDTO> groupRightsDTOS = new ArrayList<>();
        if(userModel.getGroupRights()!=null&&!userModel.getGroupRights().isEmpty()) {
            for (GroupRights groupRight : userModel.getGroupRights()) {
                GroupRightsDTO grDTO = new GroupRightsDTO.Builder()
                        .setId(groupRight.getId()).setGroupName(groupRight.getGroup().getName()).setRole(groupRight.getRole()).setUsername(userModel.getUsername())
                        .build();
                groupRightsDTOS.add(grDTO);
            }
        }
        UserDTO.Builder uDTO=new UserDTO.Builder()
                .setId(userModel.getId())
                .setUsername(userModel.getUsername())
                .setGroupRightsDTO(groupRightsDTOS);
        return uDTO.build();
    }
}
