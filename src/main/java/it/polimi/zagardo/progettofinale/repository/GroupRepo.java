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

    //ritorna un gruppo con un determinato nome
    Optional<GroupModel> findGroupModelByName(String name);

    //ritorna tutti i gruppi a cui fa parte un utente (tranne se è WAITING)
    @Query(
            "SELECT g FROM GroupModel g INNER JOIN GroupRights gr ON g.id = gr.group.id INNER JOIN UserModel u ON u.id = gr.user.id WHERE u.username = ?1"
    )
    List<GroupModel> findGroupByUsername(String username);
}
