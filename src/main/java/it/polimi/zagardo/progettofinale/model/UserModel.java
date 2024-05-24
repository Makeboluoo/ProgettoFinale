package it.polimi.zagardo.progettofinale.model;

import it.polimi.zagardo.progettofinale.model.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Event> events; //eventi a cui partecipa

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<GroupRights> groupRights;

//    @OneToMany(mappedBy = "user")
//    private List<Comment> comments;

//    @OneToMany(mappedBy = "creator", orphanRemoval = true)
//    private List<Event> createdEvents;

//    TODO Da capire per ogni classe che costruttori dare e perché!!
    public UserModel(String username, String password, List<GroupRights> groupRights) {
        this.username = username;
        this.password = password;
        this.groupRights = groupRights;
    }

    //da capire se alla fine serve a qualcosa

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserModel userModel)) return false;
        return Objects.equals(getId(), userModel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return groupRights==null||groupRights.isEmpty()?new ArrayList<>():
                groupRights.stream()
                        .map(GroupRights::getRole)
                        .map(Role::name)
                        .map(r->"ROLE_"+r)
                        .map(r->new SimpleGrantedAuthority(r))
                        .toList();

    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

}
