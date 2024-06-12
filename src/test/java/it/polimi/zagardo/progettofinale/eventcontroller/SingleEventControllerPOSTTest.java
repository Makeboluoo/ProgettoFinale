package it.polimi.zagardo.progettofinale.eventcontroller;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
public class SingleEventControllerPOSTTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventFacade eventFacade;


    @Test
    public void testSingleEvent_Success() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(152L);
        userModel.setUsername("Maria");
        userModel.setPassword("root");


        // Mocking eventFacade behavior
        long idEvent = 27;
        SingleEventDTO event = new SingleEventDTO.Builder()
                .setId(idEvent)
                .setTitle("Evento1")
                .setDescription("primo")
                .setDateTime(LocalDateTime.now())
                .setIdGroup(1002)
                .setGroupName("gruppoMyEvents")
                .setCreator("Maria")
                .setIdUser(152)
                .setRole(Role.Administrator)
                .setParticipants(Collections.singletonList(new GroupRightsDTO.Builder()
                        .setId(1652)
                        .setGroupName("gruppoMyEvents")
                        .setRole(Role.Administrator)
                        .setUsername("Maria")
                        .build()))
                .build();

        when(eventFacade.singleEvent(idEvent, userModel)).thenReturn(event);

        HttpSession session = mockMvc.perform(post("/user/login")
                        .param("username", "Maria")
                        .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"))
                .andReturn()
                .getRequest()
                .getSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/event/singleEvent")
                        .param("id_event", String.valueOf(idEvent))
                        .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events/single_event"));

    }

    @Test
    public void testSingleEvent_EventNotFound() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(152L);
        userModel.setUsername("Maria");
        userModel.setPassword("root");

        // Mocking eventFacade behavior
        long idEvent = 27;
        when(eventFacade.singleEvent(idEvent, userModel)).thenReturn(null);

        HttpSession session = mockMvc.perform(post("/user/login")
                        .param("username", "Maria")
                        .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"))
                .andReturn()
                .getRequest()
                .getSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/event/singleEvent")
                        .param("id_event", String.valueOf(idEvent))
                        .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events/single_event"));
    }
}
