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
    @Column(nullable = false,unique = true)
    private String name;
//    @Column(nullable = false,updatable = false,insertable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP()")
//    @CurrentTimestamp
//    private LocalDateTime creationDay;
    //todo questa sopra non va bene, non esiste a quanto pare esiste al massimo @CreationTimestamp. Per ora devo anche mettere manualmente la data
//    @CreationTimestamp
    @Column(nullable = false
//            ,updatable = false,insertable = false
            )
    private LocalDateTime creationDay;
    //todo: secondo me un gruppo senza utenti deve essere eliminato ma non so come fare
    @OneToMany(mappedBy = "group")
    private List<GroupRights> groupRights;
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

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
}
