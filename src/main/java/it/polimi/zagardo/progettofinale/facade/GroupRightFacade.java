package it.polimi.zagardo.progettofinale.facade;

import it.polimi.zagardo.progettofinale.dto.GroupRightsDTO;
import it.polimi.zagardo.progettofinale.mapper.GroupMapper;
import it.polimi.zagardo.progettofinale.mapper.GroupRightsMapper;
import it.polimi.zagardo.progettofinale.model.GroupRights;
import it.polimi.zagardo.progettofinale.model.UserModel;
import it.polimi.zagardo.progettofinale.service.def.GroupRightsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupRightFacade {
    private final GroupRightsService groupRightsService;
    private final GroupRightsMapper mapper;

    public GroupRightsDTO upgradeRole(long idGroupRight) {
        //fa l' upgrade del ruolo e poi lo restituisce come DTO
        GroupRights groupRights = groupRightsService.upgradeRole(idGroupRight);
        return mapper.toGroupRightDTO(groupRights);
    }

    public GroupRightsDTO downgradeRole(long idGroupRight) {
        //fa il downgrade del ruolo e poi lo restituisce come DTO
        GroupRights groupRights = groupRightsService.downgradeRole(idGroupRight);
        return mapper.toGroupRightDTO(groupRights);
    }
}
