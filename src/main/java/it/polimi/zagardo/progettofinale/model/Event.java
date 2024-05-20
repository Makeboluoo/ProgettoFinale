package it.polimi.zagardo.progettofinale.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"id_creator","id_group","title"})})
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "id_creator",nullable = false)
    private UserModel creator;
    @ManyToOne
    @JoinColumn(name = "id_group",nullable = false)
    private GroupModel group;

    //partecipante
    @ManyToMany(mappedBy = "events", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private List<UserModel> participants;

    @OneToMany(mappedBy = "event",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private List<Comment> comments;
//    In caso possiamo aggiungere questo

//    private long maxNumber;
//    private enum city;
//    private boolean openToPublic;

}
