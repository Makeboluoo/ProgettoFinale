package it.polimi.zagardo.progettofinale.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //titolo dell'evento
    @Column(nullable = false)
    private String title;
    //descrizione dell'evento
    private String description;
    //data dell'evento
    @Column(nullable = false)
    private LocalDateTime dateTime;
    //creatore dell'evento sotto forma di groupRight (connessione utente-gruppo)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_groupRight")
    private GroupRights creatorGR;

    //partecipanti sotto forma di groupRight
    @ManyToMany(mappedBy = "events")
    private List<GroupRights> participants;

    //lista di commenti sotto all'evento
    @OneToMany(mappedBy = "event",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private List<Comment> comments;

    public Event(String title, String description, LocalDateTime dateTime, GroupRights creatorGR,  List<GroupRights> participants, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
        this.creatorGR = creatorGR;
        this.participants = participants;
        this.comments = comments;
    }


}
