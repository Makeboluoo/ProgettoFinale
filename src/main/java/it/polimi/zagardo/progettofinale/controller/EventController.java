package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.EventDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.exception.EventsNotFoundException;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {

    private final EventFacade eventFacade;

    //TODO: da fare con DTO!
    @GetMapping(path = "/myEvents")
    public String myEvents(Model model){
        List<PrivateEventDTO> events = eventFacade.myEvents();
        if (!events.isEmpty()) {
            model.addAttribute("events", events);
            return "my_events";
        }
        else{
            //TODO: crea e sviluppa exception
            throw new EventsNotFoundException();
        }
    }

//    @GetMapping(path = "/allEvents")
//    public String allEvents(Model model){
//        List<EventDTO> events = eventFacade.allEvents();
//        model.addAttribute("events", events);
//            return "all_events";
//    }



    @PostMapping(path = "/creation")
    public String creationEvent(@RequestParam("title") String title, @RequestParam("description") String description,
                                @RequestParam("dateTime") LocalDateTime dateTime, Model model, HttpSession session){
        PrivateEventDTO privateEventDTO = eventFacade.creationEvent(title, description, dateTime, (String)session.getAttribute("groupName"));
        if (privateEventDTO != null) {
            model.addAttribute("event", privateEventDTO);
            return "events/event";
        }
        else return "group/group_creation_failed";
    }

//    @PostMapping(path = "/singleEvent")
//    public String singleEvent(@RequestParam("id_event") long id, Model model){
//        eventFacade.singleEvent(id, model);
//    }

}
