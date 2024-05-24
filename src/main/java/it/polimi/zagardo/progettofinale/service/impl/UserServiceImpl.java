package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public boolean checkCredentials(String username, String password) {
        String encryptedPassword = DigestUtils.sha256Hex(password);
        Optional<UserModel> userOptional = userRepo.findByUsernameAndPassword(username, encryptedPassword);
        return userOptional.isPresent();
    }

    @Override
    public void createAccount(String username, String password) {
        UserModel user = new UserModel();
        user.setUsername(username);
        String encryptedPassword = DigestUtils.sha256Hex(password);
        user.setPassword(encryptedPassword);
        userRepo.save(user);
    }

//    @Override
//    public UserModel findUserByUsername(String username) {
//        Optional<UserModel> userOptional = userRepo.findUserByUsername(username);
//        return userOptional.orElse(null);
//    }

//    @Override
//    @Transactional
//    public void updateUser(UserModel user) {
//        UserModel oldUser = findUserByUsername(user.getUsername());
//        oldUser.getEvents().clear();
//        oldUser.setEvents(user.getEvents());
//        oldUser.setGroupRights(user.getGroupRights());
////        oldUser.setComments(user.getComments());
//        userRepo.save(oldUser);
//    }

//    eventualmente fare il metodo per vedere se la sessione è ancora attiva
//    public boolean checkSession()
}
