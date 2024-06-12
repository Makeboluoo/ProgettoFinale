package it.polimi.zagardo.progettofinale.groupcontroller;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


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

        HttpSession session = mockMvc.perform(post("/user/login")
                        .param("username", "Marco")
                        .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"))
                .andReturn()
                .getRequest()
                .getSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/group/search")
                        .param("nameSearch", "gruppoMyEvents")
                        .sessionAttr("groupName", "gruppoMyEvents")
                        .session((MockHttpSession) session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/group/group"));
    }
    @Test
    public void testSearchGroupNOTOK() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(152L);
        userModel.setUsername("Maria");
        userModel.setPassword("root");

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
        when(groupFacade.findGroupByName("gruppoNonEsistente")).thenReturn(group);
        when(groupFacade.getRoleFromGroup(group, userModel)).thenReturn(Role.Administrator);
        when(groupFacade.getAdminUsername(group)).thenReturn("Maria");

        HttpSession session = mockMvc.perform(post("/user/login")
                        .param("username", "Marco")
                        .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"))
                .andReturn()
                .getRequest()
                .getSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/group/search")
                        .param("nameSearch", "gruppoNonEsistente")
                        .sessionAttr("groupName", "gruppoNonEsistente")
                        .session((MockHttpSession) session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("group/group_not_found"));
    }
}