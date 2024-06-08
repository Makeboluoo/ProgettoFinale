package it.polimi.zagardo.progettofinale.userfacade;

import it.polimi.zagardo.progettofinale.facade.UserFacade;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoginCheck {
    @Autowired
    private UserFacade userFacade;

    @Test
    public void testLoginOK() {
        String username = "Marco";
        String password = "root";
        userFacade.loginCheck(username, password);
        Assertions.assertTrue(userFacade.loginCheck(username, password));
    }

    @Test
    public void testLoginNOOK() {
        String username = "Marco";
        String password = "raat";
        userFacade.loginCheck(username, password);
        Assertions.assertFalse(userFacade.loginCheck(username, password));
    }

}
