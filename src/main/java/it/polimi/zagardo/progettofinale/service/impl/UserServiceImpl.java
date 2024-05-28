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
        //viene criptata la password usata
        String encryptedPassword = DigestUtils.sha256Hex(password);
        //si controlla se esiste un utente con determinati username e password criptata
        Optional<UserModel> userOptional = userRepo.findByUsernameAndPassword(username, encryptedPassword);
        return userOptional.isPresent();
    }

    //todo dovremmo usare un @Transactional?
    @Override
    public void createAccount(String username, String password) {
        //si crea un nuovo utente
        UserModel user = new UserModel();
        //si setta il suo username
        user.setUsername(username);
        //si cripta la password che ha utilizzato
        String encryptedPassword = DigestUtils.sha256Hex(password);
        //si setta la sua password criptata
        user.setPassword(encryptedPassword);
        //si salva nel database il nuovo utente
        userRepo.save(user);
    }
}
