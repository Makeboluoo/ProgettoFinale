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
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //prendi la lista dei gruppi di cui l'utente è membro e convertili in DTO
        List<GroupModel> g= groupService.findAllGroups(userModel);
        return mapper.toGroupDTO(g);
    }

    public GroupDTO createGroup(String name) {
        //controlla se esiste un gruppo con quel nome
        boolean exist = groupService.findIfExistGroupByName(name);
        if (!exist) {
            //prendi lo user in sessione
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //creo il gruppo
            GroupModel g = groupService.createGroup(name);
            //crea il collegamento tra utente e gruppo attraverso GroupRight
            GroupRights groupRights = groupRightsService.addGroupRight(userModel, g, Role.Administrator);
            return mapper.toGroupDTO(g);
        } else {
            return null;
        }
    }

    public SingleGroupDTO findGroupByName(String name) {
        //cerca il gruppo per il nome
        if (groupService.findGroupByName(name)!= null)
            return mapper.toSingleGroupDTO(groupService.findGroupByName(name));
        return null;
    }

    public Role getRoleFromGroup(SingleGroupDTO group) {
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for(GroupRightsDTO gr: group.getGroupRightsDTOS()){
            //cerca il collegamento groupRight tra lo user in sessione e il determinato gruppo, dopo prende il ruolo di quello user nel gruppo
            if(gr.getUsername().equals(userModel.getUsername()))return gr.getRole();
        }
        return null;
    }

    public String getAdminUsername(SingleGroupDTO group) {
        for(GroupRightsDTO gr: group.getGroupRightsDTOS()){
            //cerca lo user administrator del gruppo e ritorna il suo nome
            if(gr.getRole()==Role.Administrator)return gr.getUsername();
        }
        return null;
    }

    public boolean joinGroup(String groupName) {
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //cerca un gruppo attraverso il nome
        GroupModel groupModel = groupService.findGroupByName(groupName);
        //cerca il groupRight che collega lo user in sessione con il gruppo trovato sopra
        GroupRights gr = groupRightsService.searchGroupRightByIds(userModel.getId() ,groupModel.getId());
        //se non trova il groupRight (quindi l'utente non è effettivamente un membro del gruppo) allora crea un collegamento con ruolo Waiting
        if(gr == null)
            groupRightsService.addGroupRight(userModel, groupModel, Role.Waiting);
        return gr != null;
    }

    public void deleteGroup(String groupName) {
        //cerca il gruppo con il determinato nome
        GroupModel groupModel = groupService.findGroupByName(groupName);
        //prendi la lista di eventi pubblicati in quel gruppo
        List<Event> events = eventService.findSingleGroupEvents(groupModel);
        for(Event e: events){
            //eliminiamo ogni evento
            eventService.deleteEvent(e);
        }
        //infine elimino il gruppo
        groupService.deleteGroup(groupModel);
    }
}
