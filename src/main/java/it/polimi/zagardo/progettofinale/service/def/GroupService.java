package it.polimi.zagardo.progettofinale.service.def;

import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.UserModel;

import java.util.List;

public interface GroupService {
    GroupModel createGroup(String name);

//    GroupModel findGroupByName(String name);

    List<GroupModel> findAllGroups(UserModel userModel);

    boolean findIfExistGroupByName(String name);

    GroupModel findGroupByName(String name);

    void deleteGroup(GroupModel groupModel);
}
