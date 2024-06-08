package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.mapper.GroupMapper;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetAdminUsername {
    @Autowired
    private GroupFacade groupFacade;

    @Test
    public void testGetAdminUsernameOK() {
        String groupName = "gruppo1";
        SingleGroupDTO group = groupFacade.findGroupByName(groupName);
        String adminUsername = groupFacade.getAdminUsername(group);
        Assertions.assertNotNull(adminUsername);
    }
}
