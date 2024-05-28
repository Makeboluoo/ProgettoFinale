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

    Optional<GroupRights> findByUser_IdAndGroup_Id(long idUser, long idGroup);

    List<GroupRights> findByGroup_IdAndRole(long idGroup, Role role);



}
