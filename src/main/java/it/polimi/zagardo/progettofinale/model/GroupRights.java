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
    //grazie paolo mi hai salvato

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "group_id",nullable = false)
    private GroupModel group;

    @OneToMany(mappedBy = "groupRights", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(mappedBy = "creatorGR", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> createdEvents;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Event> events; //eventi a cui partecipa

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
