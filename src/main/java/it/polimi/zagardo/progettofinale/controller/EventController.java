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

    //passa alla pagina html my_events la lista di eventi a cui l'utente in sessione partecipa
    @GetMapping(path = "/myEvents")
    public String myEvents(Model model){
        List<PrivateEventDTO> events = eventFacade.myEvents();
        model.addAttribute("events", events);
        return "events/my_events";
    }


    //passa alla pagina html allEvents la lista di eventi pubblicati nei gruppi di cui l'utente in sessione è membro
    @GetMapping(path = "/allEvents")
    public String allEvents(Model model){
        List<PrivateEventDTO> events = eventFacade.allEvents();
        model.addAttribute("events", events);
            return "events/allEvents";
    }


    //Crea un evento in un determinato gruppo
    @PostMapping(path = "/creation")
    public String creationEvent(@RequestParam("title") String title, @RequestParam("description") String description,
                                @RequestParam("dateTime") LocalDateTime dateTime, Model model, HttpSession session){
        //crea un evento e passa il DTO di questo evento
        PrivateEventDTO privateEventDTO = eventFacade.creationEvent(title, description, dateTime, (String)session.getAttribute("groupName"));
        if (privateEventDTO != null) {
            model.addAttribute("event", privateEventDTO);
            return "events/event";
        }
        //in caso la creazione dell'evento fallisca
        else return "group/group_creation_failed";
    }

    //cerca e passa il DTO di un singolo evento con id = idEvent
    @PostMapping(path = "/singleEvent")
    public String singleEvent(@RequestParam("id_event") long idEvent, Model model){
        SingleEventDTO event = eventFacade.singleEvent(idEvent);
        //se non trova alcun evento con quell'id torna la pagina con l'errore
        if(event == null) model.addAttribute("error", "Errore nel caricare l'evento, torna indietro e riprova");
        model.addAttribute("event", event);
        return "events/single_event";
    }

    //L'utente partecipa a un determinato evento
    @PostMapping("/participate")
    public String participateEvent(@RequestParam("id_event") long id_event,@RequestParam("id_user") long id_user, Model model){
        //Cerca l'evento a cui l'utente vuole partecipare
        SingleEventDTO event = eventFacade.singleEvent(id_event);
        //Controlla se l'utente fosse già un partecipante o meno
        boolean wasAlreadyParticipant = eventFacade.partecipate(id_event, id_user);
        model.addAttribute("event", event);
        model.addAttribute("error", "Ora sei un partecipante");
        //nel caso in cui l'utente fosse già un partecipante passa il messaggio di errore alla pagina single_event qui sotto
        if (wasAlreadyParticipant) model.addAttribute("error", "Eri già un partecipante!");
        return "events/single_event";
    }

    //l'utente decide di non partecipare più a un determinato evento
    @PostMapping("/resign")
    public String resignEvent(@RequestParam("id_event") long id_event,@RequestParam("id_user") long id_user, HttpSession session, Model model){
        //Controlla se l'utente fosse già un partecipante o meno
        boolean wasAlreadyParticipant = eventFacade.partecipate(id_event, id_user);
        //Cerca l'evento che l'utente vuole abbandonare
        SingleEventDTO event = eventFacade.singleEvent(id_event);
        model.addAttribute("event", event);
        model.addAttribute("error", "You are no longer a participant!");
        //nel caso in cui l'utente non fosse già un partecipante passa il messaggio di errore alla pagina single_event qui sotto
        if (!wasAlreadyParticipant) model.addAttribute("error", "You can't resign an event you didn't join: you were already not a participant");
        else eventFacade.resign(id_event, id_user);
        return "events/single_event";
    }

    //l'utente elimina un evento
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam("id_event") long id_event, Model model){
        //Cerca l'evento che l'utente vuole eliminare
        SingleEventDTO event = eventFacade.singleEvent(id_event);
        //L'evento viene eliminato e si torna alla pagina html event_eliminated
        eventFacade.eliminateEvent(event);
        model.addAttribute("error", "You have now eliminated the event!");
        return "events/event_eliminated";
    }


}
