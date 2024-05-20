package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.exception.GroupRightsNotFoundException;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import it.polimi.zagardo.progettofinale.repository.GroupRightsRepo;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class GroupRightsServiceImpl implements GroupRightsService {
    private final GroupRightsRepo groupRightsRepo;

    @Override
    @Transactional
    public GroupRights addGroupRight(UserModel user, GroupModel group, Role role) {
        GroupRights groupRight = new GroupRights(user, group, role);
        groupRightsRepo.save(groupRight);
        return groupRight;
    }

    @Override
    public GroupRights searchMembership(UserModel user, GroupModel group) {
        Optional<GroupRights> membershipOptional = groupRightsRepo.findMembership(user, group);
        return membershipOptional.orElse(null);
    }

    @Override
    @Transactional
    public void updateMembership(String groupName,long idUser,Role r) {
        GroupRights g=groupRightsRepo.findByUser_IdAndGroup_Name(idUser,groupName).orElseThrow(()->new GroupRightsNotFoundException(idUser,groupName));
        g.setRole(r);
        groupRightsRepo.save(g);
    }

    @Override
    @Transactional
    public boolean deleteRights(long id,String name){
        GroupRights g=groupRightsRepo.findByUser_IdAndGroup_Name(id,name).orElseThrow(()->new GroupRightsNotFoundException(id,name));
        groupRightsRepo.delete(g);
        return true;
    }
}
