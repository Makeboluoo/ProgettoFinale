package it.polimi.zagardo.progettofinale.usercontroller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.mock.web.MockHttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class RegisterControllerPOSTTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegisterSuccess() throws Exception {
        MockHttpSession session = new MockHttpSession();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .param("username", "Francesco")
                        .param("password", "root")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/home"));

        // Verifica che il nome utente sia stato impostato correttamente nella sessione
        Object usernameInSession = session.getAttribute("username");
        assert usernameInSession != null && usernameInSession.equals("Francesco");
    }

    @Test
    public void testRegisterFailure() throws Exception {
        MockHttpSession session = new MockHttpSession();

        // Simula un utente già esistente
        mockMvc.perform(MockMvcRequestBuilders.post("/user/register")
                        .param("username", "Marco")
                        .param("password", "root")
                        .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register_login_logout_profile/registration_failed"));

        // Verifica che il nome utente non sia stato impostato nella sessione
        Object usernameInSession = session.getAttribute("username");
        assert usernameInSession == null;
    }

}
