package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    //ritorna un utente con password e username fissati
    Optional<UserModel> findByUsernameAndPassword(String username,String password);

    //ritorna un utente con un determinato nome
    @Query("SELECT u FROM UserModel u WHERE u.username = ?1")
    Optional<UserModel> findUserByUsername(String username);


}
