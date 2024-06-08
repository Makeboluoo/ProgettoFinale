package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetRoleFromGroup {
    @Autowired
    private GroupFacade groupFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void testGetRoleFromGroupOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        String groupName = "gruppo1";
        SingleGroupDTO group = groupFacade.findGroupByName(groupName);
        Role role = groupFacade.getRoleFromGroup(group, userModel);
        Assertions.assertNotNull(role);
        org.assertj.core.api.Assertions.assertThat(role.name()).isEqualTo(Role.Administrator.name());
    }

    @Test
    public void testGetRoleFromGroupNONOK() {
        UserModel userModel = userRepo.findById(2L).orElse(null);
        String groupName = "gruppo4";
        SingleGroupDTO group = groupFacade.findGroupByName(groupName);
        Role role = groupFacade.getRoleFromGroup(group, userModel);
        Assertions.assertNull(role);
    }
}
