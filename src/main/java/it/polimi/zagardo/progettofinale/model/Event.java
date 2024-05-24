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
//todo sta cosa sotto non mi ispira: non penso che un utente non possa mettere due eventi con lo stesso nome anche a distanza di tempo
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id_groupRight","title"})})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    //todo secondo me ha MOLTO più senso avere un solo collegamento con group-right e non due collegamenti con user e group
//    @ManyToOne
//    @JoinColumn(name = "id_creator")
//    private UserModel creator;
//    @ManyToOne
//    @JoinColumn(name = "id_group")
//    private GroupModel group;
    @ManyToOne
    @JoinColumn(name = "id_groupRight")
    private GroupRights creatorGR;

    //todo stessa cosa per i partecipanti: chissene degli user, a me importano solo i groupright
    //partecipante
    @ManyToMany(mappedBy = "events")
    private List<GroupRights> participants;

    @OneToMany(mappedBy = "event",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private List<Comment> comments;

    public Event(String title, String description, LocalDateTime dateTime,/* UserModel creator, GroupModel group,*/ GroupRights creatorGR,  List<GroupRights> participants, List<Comment> comments) {
        this.title = title;
        this.description = description;
        this.dateTime = dateTime;
//        this.creator = creator;
//        this.group = group;
        this.creatorGR = creatorGR;
        this.participants = participants;
        this.comments = comments;
    }

    //    In caso possiamo aggiungere questo

//    private long maxNumber;
//    private enum city;
//    private boolean openToPublic;

}
