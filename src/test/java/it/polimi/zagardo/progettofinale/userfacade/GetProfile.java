package it.polimi.zagardo.progettofinale.userfacade;

import it.polimi.zagardo.progettofinale.dto.UserDTO;
import it.polimi.zagardo.progettofinale.facade.UserFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetProfile {
    @Autowired
    private UserFacade userFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        UserDTO userDTO = userFacade.getProfile(userModel);
        Assertions.assertNotNull(userDTO);
    }
}
