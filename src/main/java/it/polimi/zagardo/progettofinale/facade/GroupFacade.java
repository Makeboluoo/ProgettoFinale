package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.mapper.GroupMapper;
import it.polimi.zagardo.progettofinale.mapper.UserMapper;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import it.polimi.zagardo.progettofinale.service.def.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupFacade {
    private final GroupService groupService;
    private final GroupRightsService groupRightsService;
    private final EventService eventService;
    private final GroupMapper mapper;


    public List<GroupDTO> getGroups(){
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<GroupModel> g= groupService.findAllGroups(userModel);
        return mapper.toGroupDTO(g);
    }

    public GroupDTO createGroup(String name) {
        boolean exist = groupService.findIfExistGroupByName(name);
        if (!exist) {
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //            creo il gruppo
            GroupModel g = groupService.createGroup(name);
            GroupRights groupRights = groupRightsService.addGroupRight(userModel, g, Role.Administrator);
            return mapper.toGroupDTO(g);
        } else {
            return null;
        }
    }

    public SingleGroupDTO findGroupByName(String name) {
        return mapper.toSingleGroupDTO(groupService.findGroupByName(name));
    }

    public Role getRoleFromGroup(SingleGroupDTO group) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for(GroupRightsDTO gr: group.getGroupRightsDTOS()){
            if(gr.getUsername().equals(userModel.getUsername()))return gr.getRole();
        }
        return null;
    }

    public String getAdminUsername(SingleGroupDTO group) {
        for(GroupRightsDTO gr: group.getGroupRightsDTOS()){
            if(gr.getRole()==Role.Administrator)return gr.getUsername();
        }
        return null;
    }

    public boolean joinGroup(String groupName) {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GroupModel groupModel = groupService.findGroupByName(groupName);
        GroupRights gr = groupRightsService.searchGroupRightByIds(userModel.getId() ,groupModel.getId());
        if(gr == null)
            groupRightsService.addGroupRight(userModel, groupModel, Role.Waiting);
        return gr != null;
    }

    public void deleteGroup(String groupName) {
        GroupModel groupModel = groupService.findGroupByName(groupName);
        List<Event> events = eventService.findSingleGroupEvents(groupModel);
        for(Event e: events){
            eventService.deleteEvent(e);
        }
        groupService.deleteGroup(groupModel);
    }
}
