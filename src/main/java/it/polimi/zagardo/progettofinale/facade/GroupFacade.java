package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.mapper.GroupMapper;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import it.polimi.zagardo.progettofinale.service.def.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GroupFacade {
    private final GroupService groupService;
    private final GroupRightsService groupRightsService;
    private final EventService eventService;
    private final GroupMapper mapper;


    public List<GroupDTO> getGroups(UserModel userModel){
        //prendi la lista dei gruppi di cui l'utente è membro e convertili in DTO
        List<GroupModel> g= groupService.findAllGroups(userModel);
        return mapper.toGroupDTO(g);
    }

    public GroupDTO createGroup(String name, UserModel userModel) {
        //controlla se esiste un gruppo con quel nome
        boolean exist = groupService.findIfExistGroupByName(name);
        //se il gruppo non esiste e il nome non è vuoto
        if (!exist && !Objects.equals(name, "")) {
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

    public Role getRoleFromGroup(SingleGroupDTO group, UserModel userModel) {
        for(GroupRightsDTO gr: group.getGroupRightsDTOS()){
            //cerca l'iscrizione dello user in sessione e il determinato gruppo, dopo ne prende il ruolo
            if(gr.getUsername().equals(userModel.getUsername()))return gr.getRole();
        }
        return null;
    }

    public String getAdminUsername(SingleGroupDTO group) {
        String adminUsername = "Nessun admin";
        for(GroupRightsDTO gr: group.getGroupRightsDTOS()){
            //cerca lo user administrator del gruppo e ritorna il suo nome
            if(gr.getRole()==Role.Administrator) adminUsername = gr.getUsername();
        }
        return adminUsername;
    }

    public boolean joinGroup(String groupName, UserModel userModel) {
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

    public void leaveGroup(UserModel userModel, String groupName) {
        //prendi il gruppo da abbandonare
        GroupModel groupModel = groupService.findGroupByName(groupName);
        //prendi gli eventi di quel gruppo
        List<Event> events = eventService.findSingleGroupEvents(groupModel);
        //prendi l'iscrizione a quel gruppo
        GroupRights userGroupRight = groupRightsService.searchGroupRightByIds(userModel.getId(), groupModel.getId());
        for(Event e: events){
            //eliminiamo ogni evento che ha creato l'utente in sessione
            if(e.getCreatorGR() == userGroupRight)
                eventService.deleteEvent(e);
        }
        groupRightsService.deleteGroupRight(userGroupRight);
    }
}
