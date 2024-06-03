package it.polimi.zagardo.progettofinale.service.impl;

import it.polimi.zagardo.progettofinale.model.Event;
import it.polimi.zagardo.progettofinale.model.GroupModel;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.repository.GroupRepo;
import it.polimi.zagardo.progettofinale.service.def.GroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepo groupRepo;


    //booleano per vedere se esiste un gruppo con un determinato nome
    public boolean findIfExistGroupByName(String name) {
        return groupRepo.findGroupModelByName(name).isPresent();
    }

    @Override
    public GroupModel createGroup(String name) {
        //crea un gruppo e settare il nome e la data
        GroupModel group = new GroupModel();
        group.setName(name);
        group.setCreationDay(LocalDateTime.now());
        groupRepo.save(group);
        return groupRepo.findGroupModelByName(name).orElse(null);
    }

    //trova il gruppo attraverso il nome
    @Override
    public GroupModel findGroupByName(String name){
        return groupRepo.findGroupModelByName(name).orElse(null);
    }

    //cancella il gruppo
    @Transactional
    @Override
    public void deleteGroup(GroupModel groupModel) {
        groupRepo.delete(groupModel);
    }

    @Override
    public List<GroupModel> findAllGroups(UserModel userModel) {
        //cerca tutti i gruppi di cui è membro un utente
        return groupRepo.findGroupByUsername(userModel.getUsername());
    }


}
