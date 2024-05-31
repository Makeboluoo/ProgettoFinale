package it.polimi.zagardo.progettofinale.model;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(uniqueConstraints = { @UniqueConstraint(name = "user_group_unique_constraint", columnNames = { "user_id", "group_id" }) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GroupRights {
    @Id
    @GeneratedValue
    private Long id;

    //utente
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserModel user;

    //gruppo
    @ManyToOne
    @JoinColumn(name = "group_id",nullable = false)
    private GroupModel group;

    //commenti creati
    @OneToMany(mappedBy = "groupRights", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
    private List<Comment> comments;

    //eventi creati
    @OneToMany(mappedBy = "creatorGR", cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
    private List<Event> createdEvents;

    //eventi a cui si partecipa
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Event> events; //eventi a cui partecipa

    //ruolo all'interno del gruppo
    @Column(nullable = false)
    private Role role;

    public GroupRights(UserModel user, GroupModel group, Role role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }

    public List<Event> getEvents() {
        if(events == null){
            events = new ArrayList<>();
        }
        return events;
    }


}
