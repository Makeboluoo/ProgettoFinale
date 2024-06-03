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
        //crea un group right dati utente, gruppo e ruolo
        GroupRights groupRight = new GroupRights(user, group, role);
        groupRightsRepo.save(groupRight);
        return groupRight;
    }

    @Override
    public GroupRights searchGroupRightByIds(Long idUser, Long idGroup) {
        //cerca un group right grazie agli id di utente e gruppo
        return groupRightsRepo.findByUser_IdAndGroup_Id(idUser, idGroup).orElse(null);
    }

    @Transactional
    @Override
    public GroupRights upgradeRole(long idGroupRight) {
        //prende il group right da promuovere
        GroupRights groupRights = groupRightsRepo.findById(idGroupRight).orElse(null);
        //se era un Senior allora diventa Administrator
        if(groupRights.getRole() == Role.Senior){
            //cerca il group right dell' administrator del gruppo
            List<GroupRights> adminGroupRight = groupRightsRepo.findByGroup_IdAndRole(groupRights.getGroup().getId(), Role.Administrator);
            //gli impone il ruolo di Senior
            adminGroupRight.get(0).setRole(Role.Senior);
            //fa Administrator il groupRight di partenza
            groupRights.setRole(Role.Administrator);
        }
        //se era uno Junior lo promuove a Senior
        if(groupRights.getRole() == Role.Junior)
            groupRights.setRole(Role.Senior);
        //se era uno in Waiting lo promuove a Junior (diventa ufficialmente un membro del gruppo)
        if(groupRights.getRole() == Role.Waiting)
            groupRights.setRole(Role.Junior);
        return groupRights;
    }

    @Transactional
    @Override
    public GroupRights downgradeRole(long idGroupRight) {
        //prende il group right da declassare
        GroupRights groupRights = groupRightsRepo.findById(idGroupRight).orElse(null);
        //se era uno Junior allora lo fa diventare Waiting
        if(groupRights.getRole() == Role.Junior) {
            groupRights.setRole(Role.Waiting);
            //Elimina tutti gli eventi che aveva creato nel gruppo
            groupRights.getCreatedEvents().clear();
            //Viene tolto dagli eventi a cui partecipava nel gruppo
            groupRights.getEvents().clear();
        }
        //ser era uno Senior lo declassa a Junior
        if(groupRights.getRole() == Role.Senior)
            groupRights.setRole(Role.Junior);
        return groupRights;
    }

    @Transactional
    @Override
    public void deleteGroupRight(GroupRights groupRights) {
        //prende il group right da eliminare e lo elimina
        groupRightsRepo.findById(groupRights.getId()).ifPresent(groupRightsRepo::delete);
    }


}
