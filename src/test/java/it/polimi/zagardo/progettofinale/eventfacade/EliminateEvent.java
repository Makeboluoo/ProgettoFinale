package it.polimi.zagardo.progettofinale.eventfacade;

import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.EventRepo;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EliminateEvent {
    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EventRepo eventRepo;

    @Test
    @Transactional
    public void testEliminateEventOK() {
        long id_event = 18;
        UserModel userModel = userRepo.findById(1L).orElse(null);
        SingleEventDTO event = eventFacade.singleEvent(id_event, userModel);
        eventFacade.eliminateEvent(event);
        eventRepo.flush();
        Event event1 = eventRepo.findById(id_event).orElse(null);
        Assertions.assertNull(event1);
    }
}
