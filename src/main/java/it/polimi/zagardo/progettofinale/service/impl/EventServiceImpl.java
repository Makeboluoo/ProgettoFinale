package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.model.*;
import it.polimi.zagardo.progettofinale.repository.EventRepo;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private final EventRepo eventRepo;

    @Override
    public Event findEvent(String title, String description, LocalDateTime dateTime) {
        Optional<Event> eventOptional = eventRepo.findEventByTitleAndDescriptionAndDateTime(title, description, dateTime);
        return eventOptional.orElse(null);
    }

    @Override
    public Event findEventByID(long id) {
        Optional<Event> eventOptional = eventRepo.findById(id);
        return eventOptional.orElse(null);
    }

    @Override
    public Event createEvent(String title, String description, LocalDateTime dateTime, UserModel creator, GroupModel group) {
        Event event = new Event(0,title,description,dateTime,creator,group,null,null);
        eventRepo.save(event);
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
        return eventRepo.findByParticipantsContains(user);
    }
}
