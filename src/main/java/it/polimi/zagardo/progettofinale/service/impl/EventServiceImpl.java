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
        Optional<Event> eventOptional = eventRepo.findEventByTitleAndDescriptionAndDateTime(title, description, dateTime);
        return eventOptional.orElse(null);
    }

    @Override
    public Event findEventByID(long id) {
        return eventRepo.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Event createEvent(String title, String description, LocalDateTime dateTime,/* UserModel creator, GroupModel group*/ GroupRights groupRights) {
//        UserModel user = userRepo.findById(creator.getId()).get();
        Event event = new Event(title,description,dateTime,/*user,group,*/ groupRights, new ArrayList<>(),new ArrayList<>());
        eventRepo.save(event);
        groupRights.getCreatedEvents().add(event);
        return event;
    }

//    @Transactional
//    @Override
//    public void eliminateEvent(Event e) {
//        Event event = eventRepo.findById(e.getId())
//                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
//
//        // Rimuovi l'evento da tutti gli utenti partecipanti
//
//        eventRepo.delete(event);
//    }

//    @Override
//    public void deleteEvents(List<Event> events){
//        eventRepo.deleteAll(events);
//    }

    @Override
    public List<Event> findAllEvents(long idUser) {
        return eventRepo.findAllGroupEventsByUser(idUser, Role.Waiting);
    }

    @Override
    public UserModel findSingleParticipant(long idEvent, long idUser) {
        return eventRepo.findParticipantById(idEvent, idUser).orElse(null);
    }

    @Transactional
    @Override
    public void participate(Event event, long idUser) {
        GroupRights groupRights = groupRightsRepo.findByUser_IdAndGroup_Id(idUser, event.getCreatorGR().getGroup().getId()).orElse(null);
        if (groupRights != null) groupRights.getEvents().add(event);
    }

    @Override
    public List<Event> findMyEvents(Long idUser) {
        return eventRepo.findEventsByParticipant(idUser);
    }

    @Transactional
    @Override
    public void resign(Event event, long idUser) {
        GroupRights groupRights = groupRightsRepo.findByUser_IdAndGroup_Id(idUser, event.getCreatorGR().getGroup().getId()).orElse(null);
        if (groupRights != null) groupRights.getEvents().remove(event);
    }

    @Transactional
    @Override
    public void deleteEvent(Event e) {
        for(GroupRights participant: e.getParticipants()){
            participant.getEvents().remove(e);
        }
        e.setCreatorGR(null);
        eventRepo.delete(e);
//        UserModel u = userRepo.findById(e.getCreator().getId()).orElse(null);
//        System.out.println("SONO QUI 1. L'evento è: "+ u.getCreatedEvents().size());
//        u.getCreatedEvents().remove(e);
//        System.out.println("SONO QUI 2. L'evento è: "+ u.getCreatedEvents().size());
    }

    @Override
    public List<Event> findSingleGroupEvents(GroupModel groupModel) {

        return eventRepo.findByCreatorGR_Group_Id(groupModel.getId());
    }
}
