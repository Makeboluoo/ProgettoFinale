package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.facade.GroupRightFacade;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/groupRight")
public class GroupRightsController {

    private final GroupRightFacade groupRightFacade;

    @PostMapping(path = "/upgradeRole")
    public String upgradeRole(@RequestParam("id_gr_upgrade") long idGroupRight, Model model){
        GroupRightsDTO groupRightsDTO = groupRightFacade.upgradeRole(idGroupRight);
        model.addAttribute("newGroupRight", groupRightsDTO);
        return "groupRight/group_right_upgraded";
    }

    @PostMapping(path = "/downgradeRole")
    public String downgradeRole(@RequestParam("id_gr_downgrade") long idGroupRight, Model model){
        GroupRightsDTO groupRightsDTO = groupRightFacade.downgradeRole(idGroupRight);
        model.addAttribute("newGroupRight", groupRightsDTO);
        return "groupRight/group_right_downgraded";
    }

}
