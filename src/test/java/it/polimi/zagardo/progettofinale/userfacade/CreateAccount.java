package it.polimi.zagardo.progettofinale.userfacade;

import it.polimi.zagardo.progettofinale.facade.UserFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateAccount {
    @Autowired
    private UserFacade userFacade;

    @Test
    public void createAccountOK() {
        String username = "Mariangelo";
        String password = "root";
        userFacade.createAccount(username, password);
        Assertions.assertTrue(userFacade.loginCheck(username, password));
    }

}
