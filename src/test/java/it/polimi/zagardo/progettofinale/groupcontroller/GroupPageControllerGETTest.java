package it.polimi.zagardo.progettofinale.groupcontroller;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.SingleEventDTO;
import it.polimi.zagardo.progettofinale.facade.EventFacade;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
public class GroupPageControllerGETTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GroupFacade groupFacade;

    @Test
    public void testGroupPage() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(152L);
        userModel.setUsername("Maria");
        userModel.setPassword("root");

        // Mocking authentication
        UserDetails userDetails = userModel; // UserModel dovrebbe implementare UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = org.mockito.Mockito.mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mocking groupFacade behavior
        List<GroupDTO> groups = Collections.singletonList(new GroupDTO.Builder()
                .setId(1002)
                .setName("gruppoMyEvents")
                .build());
        when(groupFacade.getGroups(userModel)).thenReturn(groups);

        mockMvc.perform(MockMvcRequestBuilders.get("/group/groups").with(user(userDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("group/groups"));
    }
}
