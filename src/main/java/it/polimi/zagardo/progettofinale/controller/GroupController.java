package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
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

    @GetMapping(path = "/groups")
    public String groupPage(Model model){
        List<GroupDTO> groups = groupFacade.getGroups();
//        if (groups.isEmpty())
//            model.addAttribute("error", "non sei iscritto a nessun gruppo");
        model.addAttribute("groups", groups);
        return "group/groups";
    }

    @PostMapping(path = "/creation")
    public String creationGroup(@RequestParam("name") String name,Model model){

        GroupDTO group = groupFacade.createGroup(name);
        if(group!=null){
            model.addAttribute("name", group.getName());
            model.addAttribute("creationDate", LocalDate.now());
            model.addAttribute("myRole", Role.Administrator);
            model.addAttribute("admin", Role.Administrator);
            return "group/group";
        }
        return "group/group_creation_failed";
    }
    @PostMapping(path = "/search")
    public String searchGroup(@RequestParam("nameSearch") String name, Model model, HttpSession session){

        SingleGroupDTO group = groupFacade.findGroupByName(name);
        if (group!= null){
            Role myRole = groupFacade.getRoleFromGroup(group);
            String adminGroupUsername = groupFacade.getAdminUsername(group);
            model.addAttribute("group", group);
            model.addAttribute("myRole", myRole);
            model.addAttribute("admin", Role.Administrator);
            model.addAttribute("adminUsername", adminGroupUsername);
            session.setAttribute("groupName", name);
            return "/group/group";
        }
        else {
            model.addAttribute("error", "There are no groups with that name. Try Again");
            return "redirect:/group/groups";
        }
    }

    @PostMapping(path = "/join")
    public String joinGroup(@RequestParam("group_name") String groupName, Model model){
        boolean wasAlreadyMember = groupFacade.joinGroup(groupName);
        model.addAttribute("error", "You are now a member of this group");
        if(wasAlreadyMember) model.addAttribute("error", "You were already a member of this group");
        return "group/group_joined";
    }
}
