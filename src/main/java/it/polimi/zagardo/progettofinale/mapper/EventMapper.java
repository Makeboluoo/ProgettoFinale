package it.polimi.zagardo.progettofinale.mapper;

import it.polimi.zagardo.progettofinale.dto.EventDTO;
import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventMapper {

    public EventDTO toEventDTO(Event e){
        return new EventDTO(e.getId(),e.getTitle(),e.getDescription(),e.getDateTime(),e.getCreatorGR().getUser().getId(),e.getCreatorGR().getUser().getUsername(),e.getCreatorGR().getGroup().getId(),e.getCreatorGR().getGroup().getName());
    }

    public List<EventDTO> toEventDTOList(List<Event> e){
        return e.stream().map(this::toEventDTO).toList();
    }

    public PrivateEventDTO toPrivateEventDTO(Event e){
        PrivateEventDTO.Builder pDTO=new PrivateEventDTO.Builder()
                .setId(e.getId())
                .setTitle(e.getTitle())
                .setDescription(e.getDescription())
                .setDateTime(e.getDateTime())
                .setCreator((e.getCreatorGR().getUser().getUsername()));
        if(e.getCreatorGR().getGroup()==null){
            pDTO.setIdGroup(-1);
            pDTO.setGroupName("NO GROUP ASSIGNED");
        }else{
            pDTO.setGroupName(e.getCreatorGR().getGroup().getName());
            pDTO.setIdGroup(e.getCreatorGR().getGroup().getId());
        }
        return pDTO.build();
    }

    public List<PrivateEventDTO> toPrivateEventDTO(List<Event> e){
        return e.stream().map(this::toPrivateEventDTO).toList();
    }

    public SingleEventDTO toSingleEventDTO(Event e, long id, Role role){
        List<GroupRightsDTO> groupRightsDTO = new ArrayList<>();
        for(GroupRights groupRight:e.getParticipants()){
            GroupRightsDTO grDTO = new GroupRightsDTO.Builder()
                    .setId(groupRight.getId()).setGroupName(groupRight.getGroup().
                            getName()).setRole(groupRight.getRole()).setUsername(groupRight.getUser().getUsername())
                    .build();
            groupRightsDTO.add(grDTO);
        }
        SingleEventDTO.Builder sDTO=new SingleEventDTO.Builder()
                .setId(e.getId())
                .setTitle(e.getTitle())
                .setDescription(e.getDescription())
                .setDateTime(e.getDateTime())
                .setCreator((e.getCreatorGR().getUser().getUsername()))
                .setIdUser(id)
                .setRole(role)
                .setParticipants(groupRightsDTO);
        if(e.getCreatorGR().getGroup()==null){
            sDTO.setIdGroup(-1);
            sDTO.setGroupName("NO GROUP ASSIGNED");
        }else{
            sDTO.setGroupName(e.getCreatorGR().getGroup().getName());
            sDTO.setIdGroup(e.getCreatorGR().getGroup().getId());
        }
        return sDTO.build();
    }

}
