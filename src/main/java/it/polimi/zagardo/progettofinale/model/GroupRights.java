package it.polimi.zagardo.progettofinale.model;
import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(nullable = false)
    private Role role;

    public GroupRights(UserModel user, GroupModel group, Role role) {
        this.user = user;
        this.group = group;
        this.role = role;
    }


}
