package it.polimi.zagardo.progettofinale.eventfacade;

import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyEvents {
    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testMyEvents() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        List<PrivateEventDTO> myEvents = eventFacade.myEvents(userModel);
        Assertions.assertNotNull(myEvents);
        org.assertj.core.api.Assertions.assertThat(myEvents.size()).isEqualTo(0);
    }
}
