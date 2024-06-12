package it.polimi.zagardo.progettofinale.eventcontroller;

import it.polimi.zagardo.progettofinale.controller.EventController;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchByGroupControllerPOSTTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventFacade eventFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchByGroup() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(152L);
        userModel.setUsername("Maria");
        userModel.setPassword("root");

        // Mocking eventFacade behavior
        PrivateEventDTO event1 = new PrivateEventDTO.Builder()
                .setId(27)
                .setTitle("Evento1")
                .setDescription("primo")
                .setDateTime(LocalDateTime.now())
                .setIdGroup(1002)
                .setGroupName("gruppoMyEvents")
                .setCreator("Maria")
                .build();

        PrivateEventDTO event2 = new PrivateEventDTO.Builder()
                .setId(2)
                .setTitle("Evento2")
                .setDescription("wow")
                .setDateTime(LocalDateTime.now())
                .setIdGroup(1002)
                .setGroupName("gruppoMyEvents")
                .setCreator("Maria")
                .build();

        List<PrivateEventDTO> events = Arrays.asList(event1, event2);
        String selectedGroup = "gruppoMyEvents";
        when(eventFacade.getEventsByGroup(selectedGroup)).thenReturn(events);

        HttpSession session = mockMvc.perform(post("/user/login")
                        .param("username", "Maria")
                        .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"))
                .andReturn()
                .getRequest()
                .getSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/event/searchByGroup")
                        .param("selectedGroup", selectedGroup)
                        .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events/allEvents"));
    }
}
