package it.polimi.zagardo.progettofinale.eventfacade;

import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Partecipate {
    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testPartecipateOK() {
        long userID = 52;
        long eventID = 21;
        boolean wasAlreadyParticipant = eventFacade.partecipate(eventID, userID);
        Assertions.assertFalse(wasAlreadyParticipant);
    }
}
