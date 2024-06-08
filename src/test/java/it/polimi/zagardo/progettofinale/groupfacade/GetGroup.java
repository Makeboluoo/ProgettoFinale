package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.dto.GroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GetGroup {
    @Autowired
    private GroupFacade groupFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        List<GroupDTO> groupDTOS = groupFacade.getGroups(userModel);
        Assertions.assertNotNull(groupDTOS);
        org.assertj.core.api.Assertions.assertThat(groupDTOS).isNotEmpty();
    }
}
