package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.mapper.EventMapper;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import it.polimi.zagardo.progettofinale.service.def.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;
    private final GroupService groupService;
    private final GroupRightsService groupRightsService;
    private final EventMapper mapper;

    public List<PrivateEventDTO> myEvents(UserModel userModel){
        //prendi la lista di eventi a cui l'utente partecipa e convertili in DTO
        List<Event> e= eventService.findMyEvents(userModel.getId());
        return mapper.toPrivateEventDTO(e);
    }

    public PrivateEventDTO creationEvent(String title, String description, LocalDateTime dateTime, String groupName, UserModel userModel) {
        if(title==null||description==null||dateTime==null)
            return null;
        //cerca se esiste un evento con quei dati
        Event e =  eventService.findEvent(title, description, dateTime);
        //nel caso in cui non esiste ancora un evento con quei valori lo crea e lo converte in DTO sennò ritorna null
        if (e == null){
            long idGroup=groupService.findGroupByName(groupName).getId();
            GroupRights g=groupRightsService.searchGroupRightByIds(userModel.getId(),idGroup);
            Event event = eventService.createEvent(title,description,dateTime, g);
            return mapper.toPrivateEventDTO(event);
        }
        else return null;
    }

    public SingleEventDTO singleEvent(long id, UserModel userModel) {
        //prendi l'evento con id = id
        Event e = eventService.findEventByID(id);
        //prendi il groupRight dell'utente in sessione nel gruppo in cui è stato pubblicato l'evento con id = id
        Role role = groupRightsService.searchGroupRightByIds(userModel.getId(), e.getCreatorGR().getGroup().getId()).getRole();
        //ritorna il DTO
        return mapper.toSingleEventDTO(e, userModel.getId(), role);
    }

    public boolean partecipate(long idEvent, long idUser) {
        //cerca se esiste un utente con id=idUser che partecipa all'evento con id = idEvent, in caso negativo
        GroupRights g = eventService.findSingleParticipant(idEvent,idUser);
        Event e = eventService.findEventByID(idEvent);
        if (g == null) eventService.participate(e, idUser);
        //ritorna true se esiste, false se non esiste
        return g != null;
    }

    public List<PrivateEventDTO> allEvents(UserModel userModel) {
        //prendi la lista degli eventi che sono stati pubblicati nei gruppi a cui l'utente fa parte
        List<Event> events = eventService.findAllEvents(userModel.getId());
        return mapper.toPrivateEventDTO(events);
    }

    public void resign(long idEvent, long idUser) {
        //cerca l'evento con id = idEvent
        Event event = eventService.findEventByID(idEvent);
        //l'utente viene tolto dalla lista dei partecipanti
        eventService.resign(event, idUser);
    }

    public void eliminateEvent(SingleEventDTO event) {
        //cerca l'evento con id = idEvent
        Event e = eventService.findEventByID(event.getId());
        //l'utente elimina l'evento
        eventService.deleteEvent(e);
    }

    public List<PrivateEventDTO> allEventsBetween(LocalDateTime fromDateTime, LocalDateTime toDateTime, UserModel userModel) {
        //prendi la lista degli eventi che sono stati pubblicati nei gruppi a cui l'utente fa parte
        List<Event> events = eventService.findAllEventsBetween(userModel.getId(), fromDateTime, toDateTime);
        return mapper.toPrivateEventDTO(events);
    }
}
