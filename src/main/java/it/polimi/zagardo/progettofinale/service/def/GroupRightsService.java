package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.transaction.Transactional;

public interface GroupRightsService {
    @Transactional
    GroupRights addGroupRight(UserModel user, GroupModel group, Role role);

    GroupRights searchMembership(UserModel user, GroupModel group);

    @Transactional
    void updateMembership(String groupName, long idUser, Role r);

    @Transactional
    boolean deleteRights(long id, String name);

    GroupRights searchGroupRightByIds(Long idUser, Long idGroup);

    GroupRights searchGroupRightById(long idGroupRight);

    GroupRights upgradeRole(long idGroupRight);

    GroupRights downgradeRole(long idGroupRight);
}
