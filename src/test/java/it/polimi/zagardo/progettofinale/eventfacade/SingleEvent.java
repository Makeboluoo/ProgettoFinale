package it.polimi.zagardo.progettofinale.eventfacade;

import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SingleEvent {
    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    @Transactional
    public void singleEventTestOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        long id = 9;
        SingleEventDTO singleEventDTO = eventFacade.singleEvent(id, userModel);
        Assertions.assertNotNull(singleEventDTO);
        Assertions.assertEquals(id, singleEventDTO.getId());
        org.assertj.core.api.Assertions.assertThat(singleEventDTO.getRole()).isEqualTo(Role.Administrator);
    }
}
