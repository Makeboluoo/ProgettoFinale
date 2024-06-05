package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.model.*;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.repository.EventRepo;
import it.polimi.zagardo.progettofinale.repository.GroupRepo;
import it.polimi.zagardo.progettofinale.repository.GroupRightsRepo;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private final EventRepo eventRepo;
    @Autowired
    private final GroupRightsRepo groupRightsRepo;

    @Override
    public Event findEvent(String title, String description, LocalDateTime dateTime) {
        //ritorna l'evento con i campi titolo, descrizione e datetime sennò passa null
        Optional<Event> eventOptional = eventRepo.findEventByTitleAndDescriptionAndDateTime(title, description, dateTime);
        return eventOptional.orElse(null);
    }

    @Override
    public Event findEventByID(long id) {
        //ritorna l'evento con id = id, sennò passa null
        return eventRepo.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Event createEvent(String title, String description, LocalDateTime dateTime, GroupRights groupRights) {
        //crea un evento con i dati passati
        Event event = new Event(title,description,dateTime, groupRights, new ArrayList<>(),new ArrayList<>());
        eventRepo.save(event);
        //aggiunge al groupRight (dell'utente in sessione nel gruppo in cui ha pubblicato l'evento) l'evento nella lista createdEvents
        groupRights.getCreatedEvents().add(event);
        return event;
    }

    @Override
    public List<Event> findAllEvents(long idUser) {
        //ritorna tutti i gli eventi dei gruppi a cui l'utente partecipa tranne quelli in cui è in WAITING
        return eventRepo.findAllGroupEventsByUser(idUser, Role.Waiting);
    }

    @Override
    public GroupRights findSingleParticipant(long idEvent, long idUser) {
        //ritorna un determinato utente con id=idUser che partecipa a un evento con id=idEvent
        return eventRepo.findParticipantById(idEvent, idUser).orElse(null);
    }

    @Transactional
    @Override
    public void participate(Event event, long idUser) {
        //cerca il groupRight dell'utente in sessione nel gruppo dell'evento che viene passato
        //e gli aggiunge l'evento tra quelli a cui partecipa
        GroupRights groupRights = groupRightsRepo.findByUser_IdAndGroup_Id(idUser, event.getCreatorGR().getGroup().getId()).orElse(null);
        if (groupRights != null) groupRights.getEvents().add(event);
    }

    @Override
    public List<Event> findMyEvents(Long idUser) {
        //cerca gli eventi a cui l'utente partecipa
        return eventRepo.findEventsByParticipant(idUser);
    }

    @Transactional
    @Override
    public void resign(Event event, long idUser) {
        //cerca il groupRight dell'utente in sessione nel gruppo dell'evento che viene passato
        //e rimuove tale evento da quelli a cui partecipa
        GroupRights groupRights = groupRightsRepo.findByUser_IdAndGroup_Id(idUser, event.getCreatorGR().getGroup().getId()).orElse(null);
        if (groupRights != null) groupRights.getEvents().remove(event);
    }

    @Transactional
    @Override
    public void deleteEvent(Event e) {
        //Prende tutti i groupRights di un determinato evento, ovvero i partecipanti
        for(GroupRights participant: e.getParticipants()){
            //per ogni groupRight prende la lista di eventi a cui partecipa e lo rimuove
            participant.getEvents().remove(e);
        }
        eventRepo.delete(e);
    }

    @Override
    public List<Event> findSingleGroupEvents(GroupModel groupModel) {
        //prende la lista di eventi di un determinato gruppo
        return eventRepo.findByCreatorGR_Group_Id(groupModel.getId());
    }

    @Override
    public List<Event> findAllEventsBetween(Long id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        return eventRepo.findAllGroupEventsByUserBetween(id, Role.Waiting, fromDateTime, toDateTime);
    }
}
