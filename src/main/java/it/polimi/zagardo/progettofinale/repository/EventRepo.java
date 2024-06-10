package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepo extends JpaRepository<Event, Long> {

    //ritorna un evento con un determinato titolo, descrizione e data
    Optional<Event> findEventByTitleAndDescriptionAndDateTime(String title, String description, LocalDateTime dateTime);

    //ritorna tutti gli eventi a cui partecipa un utente
    @Query("SELECT e FROM Event e JOIN e.participants p WHERE p.user.id = :idUser ORDER BY e.creatorGR.group.name, e.dateTime")
    List<Event> findEventsByParticipant(@Param("idUser") long idUser);

    //ritorna tutti gli eventi pubblicati nei gruppi di un utente
    @Query("SELECT e FROM Event e JOIN e.creatorGR.group g JOIN g.groupRights gr WHERE gr.user.id = :userId AND gr.role <> :role AND e.dateTime >= CURRENT_TIMESTAMP ORDER BY e.creatorGR.group.name, e.dateTime")
    List<Event> findAllGroupEventsByUser(@Param("userId") Long userId, @Param("role") Role role);

    //ritorna una iscrizione di un utente a un evento
    @Query("SELECT p FROM Event e JOIN e.participants p WHERE e.id = :idEvent AND p.user.id = :idUser")
    Optional<GroupRights> findParticipantById(@Param("idEvent") long idEvent, @Param("idUser") long idUser);

    //ritorna una lista di eventi di un singolo gruppo
    List<Event> findByCreatorGR_Group_Id(long id);

    //ritorna tutti gli eventi con data all'interno di un intervallo
    @Query("SELECT e FROM Event e JOIN e.creatorGR.group g JOIN g.groupRights gr " +
            "WHERE gr.user.id = :userId AND gr.role <> :role " +
            "AND e.dateTime BETWEEN :startDate AND :endDate " +
            "ORDER BY e.creatorGR.group.name, e.dateTime")
    List<Event> findAllGroupEventsByUserBetween(@Param("userId") Long userId,
                                         @Param("role") Role role,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate);

}
