package it.polimi.zagardo.progettofinale.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class GroupRightsNotFoundException extends RuntimeException {
    private long idUser;
    private String groupName;

}
