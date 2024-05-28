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
    //username dell'utente
    @Column(nullable = false,unique = true)
    private String username;
    //password dell'utente
    @Column(nullable = false)
    private String password;
    //groupRights in cui è coinvolto
    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<GroupRights> groupRights;
    public UserModel(String username, String password, List<GroupRights> groupRights) {
        this.username = username;
        this.password = password;
        this.groupRights = groupRights;
    }

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
