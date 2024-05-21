package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.exception.EventsNotFoundException;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {

    private final EventFacade eventFacade;
    @GetMapping(path = "/myEvents")
    public String myEvents(Model model){
        List<PrivateEventDTO> events = eventFacade.myEvents();
        model.addAttribute("events", events);
        return "events/my_events";
    }

    @GetMapping(path = "/allEvents")
    public String allEvents(Model model){
        List<PrivateEventDTO> events = eventFacade.allEvents();
        model.addAttribute("events", events);
            return "events/allEvents";
    }

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

    @PostMapping(path = "/singleEvent")
    public String singleEvent(@RequestParam("id_event") long id, Model model){
        SingleEventDTO event = eventFacade.singleEvent(id);
        if(event == null) model.addAttribute("error", "Errore nel caricare l'evento, torna indietro e riprova");
        model.addAttribute("event", event);
        return "events/single_event";
    }

    @PostMapping("/participate")
    public String participateEvent(@RequestParam("id_event") long id_event,@RequestParam("id_user") long id_user, Model model){
        SingleEventDTO event = eventFacade.singleEvent(id_event);
        boolean wasAlreadyParticipant = eventFacade.partecipate(id_event, id_user);
        model.addAttribute("event", event);
        model.addAttribute("error", "Ora sei un partecipante");
        if (wasAlreadyParticipant) model.addAttribute("error", "Eri già un partecipante!");
        return "events/single_event";
    }

}
