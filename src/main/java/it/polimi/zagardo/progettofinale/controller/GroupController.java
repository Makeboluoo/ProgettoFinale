package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/group")
public class GroupController {
    private final GroupFacade groupFacade;


    //ritorna la pagina html groups con tutti i gruppi di cui l'utente è membro
    @GetMapping(path = "/groups")
    public String groupPage(Model model){
        //si prende lo user dalla sessione e lo si converte in DTO
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //prende la lista dei gruppi e li passa alla pagina html
        List<GroupDTO> groups = groupFacade.getGroups(userModel);
        model.addAttribute("groups", groups);
        return "group/groups";
    }

    //crea un gruppo
    @PostMapping(path = "/creation")
    public String creationGroup(@RequestParam("name") String name,Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //creazione di un gruppo (se il gruppo esiste già ritorna group=null)
        GroupDTO group = groupFacade.createGroup(name, userModel);
        //il gruppo non esiste già
        if(group!=null){
            model.addAttribute("name", name);
            model.addAttribute("creationDate", LocalDate.now());
            model.addAttribute("myRole", Role.Administrator);
            return "group/group_created";
        }
        return "group/group_creation_failed";
    }
    //cerca un determinato gruppo
    @PostMapping(path = "/search")
    public String searchGroup(@RequestParam("nameSearch") String name, Model model, HttpSession session){
        //ritorna il gruppo (se esiste) con un determinato nome
        SingleGroupDTO group = groupFacade.findGroupByName(name);
        //se il gruppo esiste
        if (group!= null){
            //prendi lo user in sessione
            UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Role myRole = groupFacade.getRoleFromGroup(group, userModel);
            String adminGroupUsername = groupFacade.getAdminUsername(group);
            model.addAttribute("group", group);
            model.addAttribute("myRole", myRole);
            model.addAttribute("admin", Role.Administrator);
            model.addAttribute("adminUsername", adminGroupUsername);
            session.setAttribute("groupName", name);
            return "/group/group";
        }
        //se il gruppo non esiste
        else {
            model.addAttribute("error", "There are no groups with that name. Try Again");
            return "group/group_not_found";
        }
    }

    //l'utente diventa un membro di un determinato gruppo
    @PostMapping(path = "/join")
    public String joinGroup(@RequestParam("group_name") String groupName, Model model){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //controlla se era già un membro di quel gruppo e nel caso non lo fosse lo diventa
        boolean wasAlreadyMember = groupFacade.joinGroup(groupName, userModel);
        model.addAttribute("error", "Your joining request has been sent. Wait for the administrator to let you join in!");
        //in caso l'utente fosse già un partecipante ritorna il messaggio sotto
        if(wasAlreadyMember) model.addAttribute("error", "You were already a member of this group");
        return "group/group_joined";
    }

    //l'utente decide di eliminare un gruppo
    @PostMapping(path = "/delete")
    public String deleteGroup(@RequestParam("group_name") String groupName){
        //eliminazione del gruppo
        groupFacade.deleteGroup(groupName);
        return "group/group_deleted";
    }
    @PostMapping(path = "/leave")
    public String leaveGroup(@RequestParam("group_name") String groupName){
        //prendi lo user in sessione
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        groupFacade.leaveGroup(userModel, groupName);
        return "group/group_left";
    }
}
