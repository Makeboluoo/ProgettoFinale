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
        return new EventDTO(e.getId(),e.getTitle(),e.getDescription(),e.getDateTime(),e.getCreator().getId(),e.getCreator().getUsername(),e.getGroup().getId(),e.getGroup().getName());
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
                .setCreator((e.getCreator().getUsername()));
        if(e.getGroup()==null){
            pDTO.setIdGroup(-1);
            pDTO.setGroupName("NO GROUP ASSIGNED");
        }else{
            pDTO.setGroupName(e.getGroup().getName());
            pDTO.setIdGroup(e.getGroup().getId());
        }
        return pDTO.build();
    }

    public List<PrivateEventDTO> toPrivateEventDTO(List<Event> e){
//        if(e==null||e.isEmpty()){
//            //TODO: crea e sviluppa exception, per ora non la includo perché mi blocca il tutto
//            throw new EventsNotFoundException();
//        }

        return e.stream().map(this::toPrivateEventDTO).toList();
    }
    public SingleEventDTO toSingleEventDTO(Event e, long id, Role role){
        SingleEventDTO.Builder pDTO=new SingleEventDTO.Builder()
                .setId(e.getId())
                .setTitle(e.getTitle())
                .setDescription(e.getDescription())
                .setDateTime(e.getDateTime())
                .setCreator((e.getCreator().getUsername()))
                .setIdUser(id)
                .setRole(role);
        if(e.getGroup()==null){
            pDTO.setIdGroup(-1);
            pDTO.setGroupName("NO GROUP ASSIGNED");
        }else{
            pDTO.setGroupName(e.getGroup().getName());
            pDTO.setIdGroup(e.getGroup().getId());
        }
        return pDTO.build();
    }

}
