package it.polimi.zagardo.progettofinale.grouprightfacade;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.facade.GroupRightFacade;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UpgradeRole {
    @Autowired
    private GroupRightFacade groupRightFacade;

    @Test
    public void testUpgradeToJunior() {
        //657
        long id = 1263;
        GroupRightsDTO groupRightsDTO = groupRightFacade.upgradeRole(id);
        Assertions.assertThat(groupRightsDTO).isNotNull();
        Assertions.assertThat(groupRightsDTO.getId()).isEqualTo(id);
        Assertions.assertThat(groupRightsDTO.getRole()).isEqualTo(Role.Junior);
    }
    @Test
    public void testUpgradeToSenior() {
        //658
        long id = 1262;
        GroupRightsDTO groupRightsDTO = groupRightFacade.upgradeRole(id);
        Assertions.assertThat(groupRightsDTO).isNotNull();
        Assertions.assertThat(groupRightsDTO.getId()).isEqualTo(id);
        Assertions.assertThat(groupRightsDTO.getRole()).isEqualTo(Role.Senior);
    }
    @Test
    public void testUpgradeToAdministrator() {
        //659
        long id = 1261;
        GroupRightsDTO groupRightsDTO = groupRightFacade.upgradeRole(id);
        Assertions.assertThat(groupRightsDTO).isNotNull();
        Assertions.assertThat(groupRightsDTO.getId()).isEqualTo(id);
        Assertions.assertThat(groupRightsDTO.getRole()).isEqualTo(Role.Administrator);
    }
}
