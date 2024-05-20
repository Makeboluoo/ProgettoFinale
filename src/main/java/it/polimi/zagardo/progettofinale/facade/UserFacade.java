package it.polimi.zagardo.progettofinale.facade;


import it.polimi.zagardo.progettofinale.dto.UserDTO;
import it.polimi.zagardo.progettofinale.mapper.EventMapper;
import it.polimi.zagardo.progettofinale.mapper.UserMapper;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper mapper;

    public boolean loginCheck(String username, String password){
        return userService.checkCredentials(username, password);
    }

    public void createAccount(String username, String password) {
        userService.createAccount(username, password);
    }

    public UserDTO getProfile() {
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mapper.toUserDTO(userModel);
    }
}
