package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.EventDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.mapper.EventMapper;
import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.service.def.EventService;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import it.polimi.zagardo.progettofinale.service.def.GroupService;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventFacade {

    private final EventService eventService;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupRightsService groupRightsService;
    private final EventMapper mapper;

    public List<PrivateEventDTO> myEvents(){
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Event> e= eventService.findAllEvents(userModel);
        return mapper.toPrivateEventDTO(e);
    }

    public PrivateEventDTO creationEvent(String title, String description, LocalDateTime dateTime, String groupName) {
        Event e =  eventService.findEvent(title, description, dateTime);
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (e == null){
            Event event = eventService.createEvent(title,description,dateTime,userModel, groupService.findGroupByName(groupName));
            return mapper.toPrivateEventDTO(event);
        }
        else return null;
    }


//    public List<EventDTO> allEvents(){
//        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        //todo: serve prendere tutti gli eventi di tutti i gruppi a cui l'utente fa parte
//    }
    //---------------------------------------------------------------------------------------------------------

//    public boolean creationEvent(@RequestParam("title") String title, @RequestParam("description") String description,
//                                @RequestParam("dateTime") LocalDateTime dateTime, Model model){
//            Event event = eventService.findEvent(title, description, dateTime);
//            if (event == null) {
//                UserModel user = (UserModel) session.getAttribute("user");
//                GroupModel group = (GroupModel) session.getAttribute("group");
//                GroupRights creator = groupRightsService.searchMembership(user, group);
//                //            creo l'evento
//                eventService.createEvent(title, description, dateTime, creator);
//                event = eventService.findEvent(title, description, dateTime);
////          aggiungi automaticamente l'evento all'interno della lista di eventi e faccio l'update
//                List<Event> events = user.getEvents();
//                events.add(event);
//                user.setEvents(events);
//                userService.updateUser(user);
//                model.addAttribute("event", event);
//                return "event";
//            } else {
//                return "event_creation_failed";
//            }
//        }
//    }
//    @PostMapping("/singleEvent")
//    public String singleEvent(long id, Model model){
//
//        Event event = eventService.findEventByID(id);
//        UserModel userModel = (UserModel) session.getAttribute("user");
//        if (event == null) {
//            return "event_not_found";
//        } else {
//            model.addAttribute("event", event);
//            model.addAttribute("user", userModel);
//
//            List<GroupRights> groupRights = event.getMembership().getGroup().getMemberships();
//            String role= Role.Junior.toString();
//            for(GroupRights m: groupRights){
//                if(userModel.equals(m.getUser())){
//                    System.out.println("Ci sono");
//                    role=m.getRole().toString();
//                }
//            }
//            model.addAttribute("role", role);
//            return "single_event";
//        }
//
//
//    }
//    @PostMapping("/participate")
//    public String participateEvent(@RequestParam("id_event") long id_event,@RequestParam("id_user") long id_user, HttpSession session, Model model){
//        if (session.getAttribute("user") != null) {
//            UserModel userModel = (UserModel) session.getAttribute("user");
//            Event event = eventService.findEventByID(id_event);
//            if (event == null) {
//                return "event_not_found";
//            } else {
//                model.addAttribute("event", event);
//                model.addAttribute("user", userModel);
//
//                // controllo che l'utente non sia già iscritto all'evento
//                for(Event e: userModel.getEvents()){
//                    if(event.getId()==e.getId()){
//                        model.addAttribute("error", "You are already a participant!");
//                        return "single_event";
//                    }
//                }
//
//                // aggiungo l'evento all'utente
//                List<Event> events = userModel.getEvents();
//                events.add(event);
//                userModel.setEvents(events);
//                userService.updateUser(userModel);
//                model.addAttribute("error", "You are now a participant!");
//                return "single_event";
//            }
//        }
//        else{
//            // L'utente non è autenticato, reindirizza alla pagina di login
//            return "redirect:/api/user/login";
//        }
//    }
//    @PostMapping("/resign")
//    public String resignEvent(@RequestParam("id_event") long id_event,@RequestParam("id_user") long id_user, HttpSession session, Model model){
//        if (session.getAttribute("user") != null) {
//            UserModel userModel = (UserModel) session.getAttribute("user");
//            Event event = eventService.findEventByID(id_event);
//            if (event == null) {
//                return "event_not_found";
//            } else {
//                model.addAttribute("event", event);
//                model.addAttribute("user", userModel);
//
//                // controllo che l'utente sia già iscritto all'evento
//                for(Event e: userModel.getEvents()){
//                    if(event.getId()==e.getId()){
//                        List<Event> events = userModel.getEvents();
//                        events.remove(event);
//                        userModel.setEvents(events);
//                        userService.updateUser(userModel);
//                        model.addAttribute("error", "You are no longer a participant!");
//                        return "single_event";
//                    }
//                }
//
//                // l'utente non partecipa di già all'evento
//                model.addAttribute("error", "You are already not a participant!");
//                return "single_event";
//            }
//        }
//        else{
//            // L'utente non è autenticato, reindirizza alla pagina di login
//            return "redirect:/api/user/login";
//        }
//    }
//
//    @PostMapping("/eliminate")
//    public String eliminateEvent(@RequestParam("id_event") long id_event,@RequestParam("id_user") long id_user, HttpSession session, Model model){
//        if (session.getAttribute("user") != null) {
//            UserModel userModel = (UserModel) session.getAttribute("user");
//            Event event = eventService.findEventByID(id_event);
//            if (event == null) {
//                return "event_not_found";
//            } else {
//                eventService.eliminateEvent(event);
//                model.addAttribute("error", "You have now eliminated the event!");
//                return "single_event";
//            }
//        }
//        else{
//            // L'utente non è autenticato, reindirizza alla pagina di login
//            return "redirect:/api/user/login";
//        }
//    }

}
