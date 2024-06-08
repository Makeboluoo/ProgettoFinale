package it.polimi.zagardo.progettofinale.eventfacade;

import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class AllEventsBetween {
    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testAllEventsBetweenOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        LocalDateTime startTime = LocalDateTime.of(2024, 6, 10, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 6, 20, 0, 0);
        List<PrivateEventDTO> privateEventDTOList = eventFacade.allEventsBetween(startTime, endTime, userModel);
        Assertions.assertThat(privateEventDTOList).isNotEmpty();
        Assertions.assertThat(privateEventDTOList).hasSize(1);
    }
}
