package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.title = :title AND e.description = :description AND e.dateTime = :dateTime")
    Optional<Event> findEvent(String title, String description, LocalDateTime dateTime);

    Optional<Event> findEventByTitleAndDescriptionAndDateTime(String title, String description, LocalDateTime dateTime);

    //todo: questa query è corretta?? voglio avere tutti gli eventi a cui il mio utente partecipa.
//    List<Event> findByParticipantsContains(UserModel user);

    @Query("SELECT e FROM Event e JOIN e.participants p WHERE p = :user")
    List<Event> findEventsByParticipant(@Param("user") UserModel user);

    @Query("SELECT p FROM Event e JOIN e.participants p WHERE e.id = :idEvent AND p.id = :idUser")
    Optional<UserModel> findParticipantById(@Param("idEvent") long idEvent, @Param("idUser") long idUser);

}
