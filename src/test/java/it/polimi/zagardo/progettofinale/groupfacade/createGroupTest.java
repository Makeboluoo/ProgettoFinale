package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class createGroupTest {
    @Autowired
    private GroupFacade groupFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void createGroupOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        String groupName = "gruppo2";
        GroupDTO groupDTO = groupFacade.createGroup(groupName, userModel);
        assertNotNull(groupDTO);
        assertThat(groupDTO.getName()).isEqualTo(groupName);
    }

    @Test
    public void createGroupNONOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        String groupName = "gruppo1";
        GroupDTO groupDTO = groupFacade.createGroup(groupName, userModel);
        assertNull(groupDTO);
    }
}
