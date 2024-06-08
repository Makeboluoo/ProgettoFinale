package it.polimi.zagardo.progettofinale.usercontroller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLogoutPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/logout"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("Logout")));
    }
}
