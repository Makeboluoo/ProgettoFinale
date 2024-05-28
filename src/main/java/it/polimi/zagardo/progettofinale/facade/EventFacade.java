package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.EventDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.mapper.EventMapper;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import it.polimi.zagardo.progettofinale.service.def.GroupService;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupRightsService groupRightsService;
    private final EventMapper mapper;

    public List<PrivateEventDTO> myEvents(){
        //prendi l'utente in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //prendi la lista di eventi a cui l'utente partecipa e convertili in DTO
        List<Event> e= eventService.findMyEvents(userModel.getId());
        return mapper.toPrivateEventDTO(e);
    }

    public PrivateEventDTO creationEvent(String title, String description, LocalDateTime dateTime, String groupName) {
        //cerca se esiste un evento con quei dati
        Event e =  eventService.findEvent(title, description, dateTime);
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //nel caso in cui non esiste ancora un evento con quei valori lo crea e lo converte in DTO sennò ritorna null
        if (e == null){
            Event event = eventService.createEvent(title,description,dateTime, groupRightsService.searchGroupRightByIds(userModel.getId(),groupService.findGroupByName(groupName).getId() ));
            return mapper.toPrivateEventDTO(event);
        }
        else return null;
    }

    public SingleEventDTO singleEvent(long id) {
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //prendi l'evento con id = id
        Event e = eventService.findEventByID(id);
        //prendi il groupRight dell'utente in sessione nel gruppo in cui è stato pubblicato l'evento con id = id
        Role role = groupRightsService.searchGroupRightByIds(userModel.getId(), e.getCreatorGR().getGroup().getId()).getRole();
        //ritorna il DTO
        return mapper.toSingleEventDTO(e, userModel.getId(), role);
    }

    public boolean partecipate(long idEvent, long idUser) {
        //cerca se esiste un utente con id=idUser che partecipa all'evento con id = idEvent, in caso negativo
        UserModel u = eventService.findSingleParticipant(idEvent,idUser);
        if (u == null) eventService.participate(eventService.findEventByID(idEvent), idUser);
        //ritorna true se esiste, false se non esiste
        return u != null;
    }

    public List<PrivateEventDTO> allEvents() {
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
}
