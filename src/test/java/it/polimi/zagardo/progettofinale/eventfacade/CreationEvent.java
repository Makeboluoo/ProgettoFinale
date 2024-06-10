package it.polimi.zagardo.progettofinale.eventfacade;

import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CreationEvent {
    @Autowired
    private EventFacade eventFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void creazioneEventoConParametriNull() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        String title = null;
        String description = null;
        LocalDateTime date = null;
        String groupName = "gruppo1";

        PrivateEventDTO privateEventDTO = eventFacade.creationEvent(title,description,date,groupName,userModel);
        Assertions.assertNull(privateEventDTO);
    }

    @Test
    public void creazioneEventoConParametriOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        String title = "Evento numero " + eventFacade.allEvents(userModel).size();
        String description = "nuovo evento";
        LocalDateTime date = LocalDateTime.of(2024,7,5,18,30);
        String groupName = "gruppo1";

        PrivateEventDTO privateEventDTO = eventFacade.creationEvent(title,description,date,groupName,userModel);
        Assertions.assertNotNull(privateEventDTO);
        Assertions.assertEquals(title, privateEventDTO.getTitle());
        Assertions.assertEquals(description, privateEventDTO.getDescription());
    }

    @Test
    public void creazioneEventoConParametriUguali() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        String title = "Evento numero " + (eventFacade.allEvents(userModel).size()-1);
        String description = "nuovo evento";
        LocalDateTime date = LocalDateTime.of(2024,6,5,18,30);
        String groupName = "gruppo1";

        PrivateEventDTO privateEventDTO = eventFacade.creationEvent(title,description,date,groupName,userModel);
        Assertions.assertNull(privateEventDTO);
    }
}
