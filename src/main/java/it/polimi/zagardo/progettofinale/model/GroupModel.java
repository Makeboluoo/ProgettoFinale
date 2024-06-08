package it.polimi.zagardo.progettofinale.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GroupModel {
    @Id
    @GeneratedValue
    private Long id;
    //nome del gruppo
    @Column(nullable = false,unique = true)
    private String name;
    //data di creazione
    @Column(nullable = false)
    private LocalDateTime creationDay;
    //membri di un gruppo
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "group", cascade = CascadeType.ALL)
    private List<GroupRights> groupRights;

    public GroupModel(String name) {
        this.name = name;
        this.groupRights = new ArrayList<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupModel that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "GroupModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDay=" + creationDay +
                '}';
    }
}
