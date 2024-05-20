package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroupRepo extends JpaRepository<GroupModel, Long> {
//    @Query("SELECT g FROM GroupModel g WHERE g.name = ?1")
//    Optional<GroupModel> findGroupByName(String name);
    Optional<GroupModel> findGroupModelByName(String name);

//    List<GroupModel> findGroupModelBy(UserModel userModel);

//    da cambiare a updateGroup generale: così tutti passaggi li facciamo prima (anche perché non ha senso lasciare updateMembership nel repo di group
   /* @Modifying
    @Query("UPDATE GroupModel g SET g. = :newMemberships WHERE g.name = :name")
    void updateMembership(List<GroupRights> newGroupRights, String name);*/

    @Query(
            "SELECT g FROM GroupModel g INNER JOIN GroupRights gr ON g.id = gr.group.id INNER JOIN UserModel u ON u.id = gr.user.id WHERE u.username = ?1"
    )
    List<GroupModel> findGroupByUsername(String username);
}
