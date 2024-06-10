package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRightsRepo extends JpaRepository<GroupRights, Long> {

    //ritorna un'iscrizione di un utente a un gruppo
    Optional<GroupRights> findByUser_IdAndGroup_Id(long idUser, long idGroup);

    //ritorna una lista di iscrizioni di un gruppo con un determinato ruolo
    List<GroupRights> findByGroup_IdAndRole(long idGroup, Role role);



}
