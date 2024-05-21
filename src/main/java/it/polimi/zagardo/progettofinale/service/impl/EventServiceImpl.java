package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.model.*;
import it.polimi.zagardo.progettofinale.repository.EventRepo;
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
    private final UserRepo userRepo;

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
    public Event createEvent(String title, String description, LocalDateTime dateTime, UserModel creator, GroupModel group) {
        UserModel user = userRepo.findById(creator.getId()).get();
        Event event = new Event(title,description,dateTime,user,group,new ArrayList<>(),new ArrayList<>());
        eventRepo.save(event);
        user.getEvents().add(event);
        return event;
    }

    @Override
    public void eliminateEvent(Event e) {
        Event event = eventRepo.findById(e.getId())
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        // Rimuovi l'evento da tutti gli utenti partecipanti

        eventRepo.delete(event);
    }

    @Override
    public void deleteEvents(List<Event> events){
        eventRepo.deleteAll(events);
    }

    @Override
    public List<Event> findAllEvents(UserModel user) {
        return eventRepo.findEventsByParticipant(user);
    }
}
