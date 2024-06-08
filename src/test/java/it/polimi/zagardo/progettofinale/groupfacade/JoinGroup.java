package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JoinGroup {
    @Autowired
    private GroupFacade groupFacade;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void joinGroupTestOK(){
        //todo nel rifarlo ricordati che Luca è già un membro di gruppo0, devi cambiare gruppo o persona
        //join di un gruppo di cui non si era membri
        String groupName = "gruppo0";
        UserModel userModel = userRepo.findById(2L).orElse(null);
        boolean alreadyJoined = groupFacade.joinGroup(groupName, userModel);
        Assertions.assertFalse(alreadyJoined);
    }

    @Test
    public void joinGroupTestNONOK(){
        //join di un gruppo di cui si era già membri
        String groupName = "gruppo1";
        UserModel userModel = userRepo.findById(1L).orElse(null);
        boolean alreadyJoined = groupFacade.joinGroup(groupName, userModel);
        Assertions.assertTrue(alreadyJoined);
    }
}
