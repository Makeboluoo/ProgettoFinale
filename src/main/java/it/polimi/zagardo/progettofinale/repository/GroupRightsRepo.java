package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupRightsRepo extends JpaRepository<GroupRights, Long> {
    @Query("SELECT m FROM GroupRights m WHERE m.user = :user AND m.group = :group")
    Optional<GroupRights> findMembership(UserModel user, GroupModel group);

    Optional<GroupRights> findByUser_IdAndGroup_Name(Long id,String nome);
}
