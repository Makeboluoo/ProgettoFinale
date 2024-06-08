package it.polimi.zagardo.progettofinale.eventcontroller;

import it.polimi.zagardo.progettofinale.controller.EventController;
import it.polimi.zagardo.progettofinale.dto.PrivateEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.mapper.EventMapper;
import it.polimi.zagardo.progettofinale.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class AllEventsControllerGETTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EventFacade eventFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAllEvents() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(152L);
        userModel.setUsername("Maria");
        userModel.setPassword("root");

        // Mocking authentication
        UserDetails userDetails = userModel; // UserModel dovrebbe implementare UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

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
        when(eventFacade.allEvents(userModel)).thenReturn(events);

        mockMvc.perform(MockMvcRequestBuilders.get("/event/allEvents").with(user(userDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events/allEvents"));
    }
}
