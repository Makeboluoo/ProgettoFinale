package it.polimi.zagardo.progettofinale.usercontroller;

import it.polimi.zagardo.progettofinale.controller.UserController;
import it.polimi.zagardo.progettofinale.facade.UserFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class LoginControllerPOSTTest {

    @Mock
    private UserFacade userFacade;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Test
    public void testLoginCheck_Success() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MockHttpSession session = new MockHttpSession();

        when(userFacade.loginCheck(anyString(), anyString())).thenReturn(true);

        mockMvc.perform(post("/user/login")
                        .param("username", "Marco")
                        .param("password", "root")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/home"))
                .andExpect(request().sessionAttribute("username", "Marco"));
    }

    @Test
    public void testLoginCheck_Failure() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        MockHttpSession session = new MockHttpSession();

        when(userFacade.loginCheck(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/user/login")
                        .param("username", "Marco")
                        .param("password", "rat")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("register_login_logout_profile/login_failed"));
    }
}
