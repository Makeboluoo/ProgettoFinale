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

import java.util.List;
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

    @Override
    public GroupRights searchGroupRightByIds(Long idUser, Long idGroup) {
        return groupRightsRepo.findByUser_IdAndGroup_Id(idUser, idGroup).orElse(null);
    }

    @Override
    public GroupRights searchGroupRightById(long idGroupRight) {
        return groupRightsRepo.findById(idGroupRight).orElse(null);
    }

    @Transactional
    @Override
    public GroupRights upgradeRole(long idGroupRight) {
        GroupRights groupRights = groupRightsRepo.findById(idGroupRight).orElse(null);
        if(groupRights.getRole() == Role.Senior){
            List<GroupRights> adminGroupRight = groupRightsRepo.findByGroup_IdAndRole(groupRights.getGroup().getId(), Role.Administrator);
            adminGroupRight.get(0).setRole(Role.Senior);
            groupRights.setRole(Role.Administrator);
        }
        if(groupRights.getRole() == Role.Junior)
            groupRights.setRole(Role.Senior);
        if(groupRights.getRole() == Role.Waiting)
            groupRights.setRole(Role.Junior);
        return groupRights;
    }

    @Transactional
    @Override
    public GroupRights downgradeRole(long idGroupRight) {
        GroupRights groupRights = groupRightsRepo.findById(idGroupRight).orElse(null);
        //todo magari se lo metti in waiting puoi cancellarlo dagli eventi del gruppo
        if(groupRights.getRole() == Role.Junior) {
            groupRights.setRole(Role.Waiting);
            groupRights.getCreatedEvents().clear();
            groupRights.getEvents().clear();
        }
        if(groupRights.getRole() == Role.Senior)
            groupRights.setRole(Role.Junior);
        return groupRights;
    }
}
