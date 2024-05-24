package it.polimi.zagardo.progettofinale.mapper;

import it.polimi.zagardo.progettofinale.dto.EventDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.exception.EventsNotFoundException;
import it.polimi.zagardo.progettofinale.model.Event;
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
//        List<Event> newEventList = new ArrayList<>();
//        for (Event singleEvent: e){
//            if(singleEvent.getParticipants().get().getRole() != Role.Waiting)
//                newEventList.add(singleEvent);
//        }

        return e.stream().map(this::toPrivateEventDTO).toList();
    }

    public SingleEventDTO toSingleEventDTO(Event e, long id, Role role){
        SingleEventDTO.Builder sDTO=new SingleEventDTO.Builder()
                .setId(e.getId())
                .setTitle(e.getTitle())
                .setDescription(e.getDescription())
                .setDateTime(e.getDateTime())
                .setCreator((e.getCreatorGR().getUser().getUsername()))
                .setIdUser(id)
                .setRole(role);
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
