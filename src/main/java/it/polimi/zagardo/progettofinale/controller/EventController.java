package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/event")
public class EventController {

    private final EventFacade eventFacade;

    //passa alla pagina html my_events la lista di eventi a cui l'utente in sessione partecipa
    @GetMapping(path = "/myEvents")
    public String myEvents(Model model){
        //prendi l'utente in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PrivateEventDTO> events = eventFacade.myEvents(userModel);
        model.addAttribute("now", LocalDateTime.now());
        model.addAttribute("events", events);
        return "events/my_events";
    }


    //passa alla pagina html allEvents la lista di eventi pubblicati nei gruppi di cui l'utente in sessione è membro
    @GetMapping(path = "/allEvents")
    public String allEvents(Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PrivateEventDTO> events = eventFacade.allEvents(userModel);
        List<String> groupNames = eventFacade.getGroupNames(events);
        model.addAttribute("groupNames", groupNames);
        model.addAttribute("events", events);
            return "events/allEvents";
    }
    //passa la lista di eventi con la data in un certo periodo
    @PostMapping(path = "/searchBetween")
    public String searchBetween(@RequestParam("fromDateTime") LocalDateTime fromDateTime, @RequestParam("toDateTime") LocalDateTime toDateTime, Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<PrivateEventDTO> events = eventFacade.allEventsBetween(fromDateTime, toDateTime, userModel);
        List<String> groupNames = eventFacade.getGroupNames(events);
        model.addAttribute("groupNames", groupNames);
        model.addAttribute("events", events);
        return "events/allEvents";
    }

    @PostMapping(path = "/searchByGroup")
    public String searchByGroup(@RequestParam("selectedGroup") String selectedGroup, Model model){
        // Logica per filtrare gli eventi in base al gruppo selezionato
        List<PrivateEventDTO> events = eventFacade.getEventsByGroup(selectedGroup);
        model.addAttribute("events", events);
        return "events/allEvents";
    }


    //Crea un evento in un determinato gruppo
    @PostMapping(path = "/creation")
    public String creationEvent(@RequestParam(value = "title", required = false) String title, @RequestParam(value = "description", required = false) String description,
                                @RequestParam(value = "dateTime", required = false) LocalDateTime dateTime, Model model, HttpSession session){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //crea un evento e passa il DTO di questo evento
        PrivateEventDTO privateEventDTO = eventFacade.creationEvent(title, description, dateTime, (String)session.getAttribute("groupName"), userModel);
        if (privateEventDTO != null) {
            model.addAttribute("event", privateEventDTO);
            return "events/event";
        }
        //in caso la creazione dell'evento fallisca
        else return "events/event_creation_failed";
    }

    //cerca e passa il DTO di un singolo evento con id = idEvent
    @PostMapping(path = "/singleEvent")
    public String singleEvent(@RequestParam("id_event") long idEvent, Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SingleEventDTO event = eventFacade.singleEvent(idEvent, userModel);
        //se non trova alcun evento con quell'id torna la pagina con l'errore
        if(event == null) model.addAttribute("error",
                "An error occurred during the loading of the event. Please go back and try again!");
        model.addAttribute("event", event);
        return "events/single_event";
    }

    //L'utente partecipa a un determinato evento
    @PostMapping("/participate")
    public String participateEvent(@RequestParam("id_event") long id_event, Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Cerca l'evento a cui l'utente vuole partecipare
        SingleEventDTO event = eventFacade.singleEvent(id_event, userModel);
        //Controlla se l'utente fosse già un partecipante o meno
        boolean wasAlreadyParticipant = eventFacade.partecipate(id_event, userModel.getId());
        model.addAttribute("event", event);
        model.addAttribute("error", "You are now a participant!");
        //nel caso in cui l'utente fosse già un partecipante passa il messaggio di errore alla pagina single_event qui sotto
        if (wasAlreadyParticipant) model.addAttribute("error", "You were already a participant!");
        return "events/single_event";
    }

    //l'utente decide di non partecipare più a un determinato evento
    @PostMapping("/resign")
    public String resignEvent(@RequestParam("id_event") long id_event, HttpSession session, Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Controlla se l'utente fosse già un partecipante o meno
        boolean wasAlreadyParticipant = eventFacade.partecipate(id_event, userModel.getId());
        //Cerca l'evento che l'utente vuole abbandonare
        SingleEventDTO event = eventFacade.singleEvent(id_event, userModel);
        model.addAttribute("event", event);
        model.addAttribute("error", "You are no longer a participant!");
        //nel caso in cui l'utente non fosse già un partecipante passa il messaggio di errore
        // alla pagina single_event qui sotto
        if (!wasAlreadyParticipant) model.addAttribute("error",
                "You can't resign an event you didn't join: you were already not a participant");
        else eventFacade.resign(id_event, userModel.getId());
        return "events/single_event";
    }

    //l'utente elimina un evento
    @PostMapping("/delete")
    public String deleteEvent(@RequestParam("id_event") long id_event, Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Cerca l'evento che l'utente vuole eliminare
        SingleEventDTO event = eventFacade.singleEvent(id_event, userModel);
        //L'evento viene eliminato e si torna alla pagina html event_eliminated
        eventFacade.eliminateEvent(event);
        model.addAttribute("error", "You have now eliminated the event!");
        return "events/event_eliminated";
    }


}
