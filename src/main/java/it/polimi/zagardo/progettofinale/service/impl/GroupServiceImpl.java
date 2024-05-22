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


    //TO-DO eventualmente da togliere: si può tranquillamente usare findGroupByName. Non c'è bisogno di un metodo booleano
    public boolean findIfExistGroupByName(String name) {
        return groupRepo.findGroupModelByName(name).isPresent();
    }

    @Override
    public GroupModel createGroup(String name) {
        GroupModel group = new GroupModel();
        group.setName(name);
//        in teoria per via delle annotations in groupModel non dovrei settare io la data di creazione
        group.setCreationDay(LocalDateTime.now());
        groupRepo.save(group);
        return groupRepo.findGroupModelByName(name).orElse(null);
    }

    @Override
    public GroupModel findGroupByName(String name){
        return groupRepo.findGroupModelByName(name).orElse(null);
    }

    @Transactional
    @Override
    public void deleteGroup(GroupModel groupModel) {
        groupRepo.delete(groupModel);
    }

    @Override
    public List<GroupModel> findAllGroups(UserModel userModel) {
        return groupRepo.findGroupByUsername(userModel.getUsername());
    }


}
