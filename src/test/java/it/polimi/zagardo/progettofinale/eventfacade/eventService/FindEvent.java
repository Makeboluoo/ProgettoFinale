package it.polimi.zagardo.progettofinale.eventfacade.eventService;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class FindEvent {
    @Autowired
    private EventService eventService;

    @Test
    public void findEvent() {
        String title = "Eventi1" ;
        String description= "aaaa";
        LocalDateTime dateTime = LocalDateTime.of(2024,6,6,1,16);
        Event e = eventService.findEvent(title, description, dateTime);
        Assertions.assertNotNull(e);
        Assertions.assertEquals(title, e.getTitle());
        Assertions.assertEquals(description, e.getDescription());
        Assertions.assertEquals(dateTime, e.getDateTime());
    }
}
