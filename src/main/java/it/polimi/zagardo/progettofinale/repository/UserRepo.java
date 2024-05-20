package it.polimi.zagardo.progettofinale.repository;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsernameAndPassword(String username,String password);
//    @Query("SELECT u FROM UserModel u WHERE u.username = ?1 AND u.password = ?2")
//    Optional<UserModel> checkCredentials(String username, String encryptedPassword);


    @Query("SELECT u FROM UserModel u WHERE u.username = ?1")
    Optional<UserModel> findUserByUsername(String username);


}
