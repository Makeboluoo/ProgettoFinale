package it.polimi.zagardo.progettofinale.commentcontroller;

import it.polimi.zagardo.progettofinale.dto.AllEventCommentsDTO;
import it.polimi.zagardo.progettofinale.dto.CommentDTO;
import it.polimi.zagardo.progettofinale.facade.CommentFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.util.ArrayList;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
@SpringBootTest
@AutoConfigureMockMvc
public class EventCommentsControllerPOSTTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CommentFacade commentFacade;

    @Test
    public void testEventComments() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(152L);
        userModel.setUsername("Maria");
        userModel.setPassword("root");

        // Mocking authentication
        UserDetails userDetails = userModel; // UserModel dovrebbe implementare UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mocking commentFacade behavior
        long eventId = 27; // Example event ID
        List<CommentDTO> commentDTOList = new ArrayList<>(); // Example list of comments DTO
        CommentDTO comment1 = new CommentDTO.Builder()
                .setId(1L)
                .setUsername("Maria")
                .setText("ciao")
                .build();
        commentDTOList.add(comment1);

        AllEventCommentsDTO allEventCommentsDTO = new AllEventCommentsDTO.Builder()
                .setIdEvent(eventId)
                .setCommentsDTO(commentDTOList)
                .build();

        when(commentFacade.getEventComments(eventId)).thenReturn(allEventCommentsDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/comment/eventComments")
                        .param("id_event", String.valueOf(eventId))
                        .with(user(userDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("comments/event_comments"));
    }
}
