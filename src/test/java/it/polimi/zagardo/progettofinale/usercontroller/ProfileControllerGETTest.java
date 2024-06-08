package it.polimi.zagardo.progettofinale.usercontroller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import it.polimi.zagardo.progettofinale.controller.UserController;
import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.dto.UserDTO;
import it.polimi.zagardo.progettofinale.facade.UserFacade;
import it.polimi.zagardo.progettofinale.mapper.UserMapper;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
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

import java.util.Collections;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerGETTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserFacade userFacade;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserController userController;

    @Test
    public void testProfilePage() throws Exception {
        // Mocking user data
        UserModel userModel = new UserModel();
        userModel.setId(102L);
        userModel.setUsername("Giorgia");
        userModel.setPassword("root");

        // Mocking authentication
        UserDetails userDetails = userModel; // UserModel dovrebbe implementare UserDetails
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Mocking userFacade behavior
        UserDTO userDTO = new UserDTO.Builder()
                .setId(102)
                .setUsername("Giorgia")
                .setGroupRightsDTO(Collections.singletonList(new GroupRightsDTO.Builder()
                        .setId(1552)
                        .setGroupName("GruppoTestProfile")
                        .setRole(Role.Administrator) // Imposta un valore valido per il ruolo
                        .setUsername("Giorgia")
                        .build()))
                .build();
        when(userFacade.getProfile(userModel)).thenReturn(userDTO);

        // Mocking userMapper behavior
        when(userMapper.toUserDTO(userModel)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile").with(user(userDetails)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register_login_logout_profile/profile"));
    }

}
