package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import jakarta.transaction.Transactional;

import java.util.List;

public interface UserService {
    boolean checkCredentials(String username, String password);

    void createAccount(String username, String password);

//    UserModel findUserByUsername(String username);
//
//    @Transactional
//    void updateUser(UserModel user);

}
