package it.polimi.zagardo.progettofinale.facade;


import it.polimi.zagardo.progettofinale.dto.UserDTO;
import it.polimi.zagardo.progettofinale.mapper.UserMapper;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper mapper;

    public boolean loginCheck(String username, String password){
        //viene controllata la reale presenza di un utente con quel username e password
        return userService.checkCredentials(username, password);
    }

    //si crea un nuovo account
    public void createAccount(String username, String password) {
        userService.createAccount(username, password);
    }

    public UserDTO getProfile(UserModel userModel) {
        return mapper.toUserDTO(userModel);
    }
}
