package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.transaction.Transactional;

public interface GroupRightsService {
    @Transactional
    GroupRights addGroupRight(UserModel user, GroupModel group, Role role);

    GroupRights searchGroupRightByIds(Long idUser, Long idGroup);

    GroupRights upgradeRole(long idGroupRight);

    GroupRights downgradeRole(long idGroupRight);
}
