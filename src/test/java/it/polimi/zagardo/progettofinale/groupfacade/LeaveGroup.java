package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LeaveGroup {
    @Autowired
    private GroupFacade groupFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    @Transactional
    public void testLeaveGroupOK() {
        UserModel userModel = userRepo.findById(1L).orElse(null);
        String groupName = "gruppo4";
        boolean alreadyMember = groupFacade.joinGroup(groupName, userModel);
        Assertions.assertTrue(alreadyMember);
        groupFacade.leaveGroup(userModel, groupName);
        //controllo che non sono più un membro (e mi iscrivo)
        boolean wasAlreadyMember = groupFacade.joinGroup(groupName, userModel);
        Assertions.assertFalse(wasAlreadyMember);
    }
}
