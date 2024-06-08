package it.polimi.zagardo.progettofinale.groupcontroller;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
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


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;


@SpringBootTest
@AutoConfigureMockMvc
public class SearchGroupControllerPOSTTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private GroupFacade groupFacade;

    @Test
    public void testSearchGroup() throws Exception {
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

        // Mocking groupFacade behavior
        List<GroupRightsDTO> groupRightsDTOS = new ArrayList<>();
        GroupRightsDTO groupRightsDTO = new GroupRightsDTO.Builder()
                .setId(1652)
                .setGroupName("gruppoMyEvents")
                .setRole(Role.Administrator)
                .setUsername("Maria")
                .build();
        groupRightsDTOS.add(groupRightsDTO);
        SingleGroupDTO group = new SingleGroupDTO.Builder()
                .setID(1002)
                .setName("gruppoMyEvents")
                .setCreationDateTime(LocalDateTime.now())
                .setGroupRightsDTOS(groupRightsDTOS)
                .build();
        when(groupFacade.findGroupByName("gruppoMyEvents")).thenReturn(group);
        when(groupFacade.getRoleFromGroup(group, userModel)).thenReturn(Role.Administrator);
        when(groupFacade.getAdminUsername(group)).thenReturn("Maria");

        mockMvc.perform(MockMvcRequestBuilders.post("/group/search")
                        .param("nameSearch", "gruppoMyEvents")
                        .sessionAttr("groupName", "gruppoMyEvents")
                        .with(user(userDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/group/group"))
                .andExpect(MockMvcResultMatchers.model().attribute("myRole", Role.Administrator))
                .andExpect(MockMvcResultMatchers.model().attribute("admin", Role.Administrator))
                .andExpect(MockMvcResultMatchers.model().attribute("adminUsername", "Maria"));
    }
}