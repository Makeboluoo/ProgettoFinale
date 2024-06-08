package it.polimi.zagardo.progettofinale.grouprightfacade;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.facade.GroupRightFacade;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DowngradeRole {
    @Autowired
    private GroupRightFacade groupRightFacade;

    //todo rifare con un id settato per Senior e uno per Junior sennò sbaglia
    @Test
    public void testDowngradeToJunior() {
        //655
        long id = 1264;
        GroupRightsDTO groupRightsDTO = groupRightFacade.downgradeRole(id);
        Assertions.assertThat(groupRightsDTO).isNotNull();
        Assertions.assertThat(groupRightsDTO.getId()).isEqualTo(id);
        Assertions.assertThat(groupRightsDTO.getRole()).isEqualTo(Role.Junior);
    }
    @Test
    public void testDowngradeToWaiting() {
        //656
        long id = 1265;
        GroupRightsDTO groupRightsDTO = groupRightFacade.downgradeRole(id);
        Assertions.assertThat(groupRightsDTO).isNotNull();
        Assertions.assertThat(groupRightsDTO.getId()).isEqualTo(id);
        Assertions.assertThat(groupRightsDTO.getRole()).isEqualTo(Role.Waiting);
    }
}
