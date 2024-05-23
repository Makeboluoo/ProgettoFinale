package it.polimi.zagardo.progettofinale.controller;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.facade.CommentFacade;
import it.polimi.zagardo.progettofinale.service.def.CommentService;
import it.polimi.zagardo.progettofinale.service.def.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentFacade commentFacade;

    @PostMapping(path = "/eventComments")
    public String eventComments(@RequestParam("id_event") long idEvent, Model model){
        AllEventCommentsDTO comments = commentFacade.getEventComments(idEvent);
        model.addAttribute("comments", comments);
        return "comments/event_comments";
    }

    @PostMapping(path = "/postComment")
    public String postComment(@RequestParam("id_event") long idEvent, @RequestParam("comment") String comment, Model model){
        AllEventCommentsDTO comments = commentFacade.postComment(idEvent, comment);
        model.addAttribute("comments", comments);
        return "comments/event_comments";
    }

}

