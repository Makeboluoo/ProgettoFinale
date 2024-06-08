package it.polimi.zagardo.progettofinale.eventfacade;

import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.service.impl.EventServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Resign {
    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private EventServiceImpl eventServiceImpl;

    //todo da applicazione funziona, qui no....
    @Test
    @Transactional
    public void testResign() {
        long userID = 2;
        long eventID = 17;
        eventFacade.resign(eventID, userID);

        GroupRights g = eventServiceImpl.findSingleParticipant(eventID, userID);
        Assertions.assertNull(g);
    }
}
