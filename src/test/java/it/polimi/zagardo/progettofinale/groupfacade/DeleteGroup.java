package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DeleteGroup {
    @Autowired
    private GroupFacade groupFacade;

    //todo cambiare il gruppo visto che gruppo3 non esiste più
    @Test
    public void testDeleteGroupOK() {
        String groupName = "gruppo3";
        groupFacade.deleteGroup(groupName);
        Assertions.assertNull(groupFacade.findGroupByName(groupName));
    }

}
