package it.polimi.zagardo.progettofinale.dto;

import java.time.LocalDateTime;
//il record è una classe particolare che definisce in creazione tutti i getter e setter e il costruttore
public record EventDTO(long id, String title, String description, LocalDateTime dateTime,long idCreator,String usernameCreator,long idGroup,String groupName) {
}
