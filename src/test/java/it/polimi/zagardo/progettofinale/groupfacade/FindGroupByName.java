package it.polimi.zagardo.progettofinale.groupfacade;

import it.polimi.zagardo.progettofinale.dto.SingleGroupDTO;
import it.polimi.zagardo.progettofinale.facade.GroupFacade;
import it.polimi.zagardo.progettofinale.facade.UserFacade;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class FindGroupByName {
    @Autowired
    private GroupFacade groupFacade;

    @Test
    public void testFindGroupByNameOK(){
        String groupName = "gruppo1";
        SingleGroupDTO singleGroupDTO = groupFacade.findGroupByName(groupName);
        assertNotNull(singleGroupDTO);
        assertThat(singleGroupDTO.getName()).isEqualTo(groupName);
    }

    @Test
    public void testFindGroupByNameNONOK(){
        String groupName = "gruppo100";
        SingleGroupDTO singleGroupDTO = groupFacade.findGroupByName(groupName);
        assertNull(singleGroupDTO);
    }
}
