package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.UserModel;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    Event findEvent(String title, String description, LocalDateTime dateTime);

    Event findEventByID(long id);

    Event createEvent(String title, String description, LocalDateTime dateTime, UserModel creator, GroupModel group);

    void eliminateEvent(Event e);

    void deleteEvents(List<Event> events);

    List<Event> findAllEvents(long idUser);

    UserModel findSingleParticipant(long idEvent, long idUser);

    void participate(Event event, long idUser);

    List<Event> findMyEvents(Long idUser);
}
