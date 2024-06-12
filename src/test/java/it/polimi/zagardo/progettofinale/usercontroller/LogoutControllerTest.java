package it.polimi.zagardo.progettofinale.usercontroller;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogoutPage() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/logout"))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
//                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Logout")));
        // Perform the login request
        HttpSession session = mockMvc.perform(post("/user/login")
                        .param("username", "Marco")
                        .param("password", "root"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/home"))
                .andReturn()
                .getRequest()
                .getSession();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/logout")
                        .session((MockHttpSession) session))
                .andExpect(status().isOk())
                .andExpect(view().name("register_login_logout_profile/logout_page"));
    }
}
