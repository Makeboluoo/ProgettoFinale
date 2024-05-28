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
//    @Column(nullable = false,updatable = false,insertable = false, columnDefinition = "DEFAULT CURRENT_TIMESTAMP()")
//    @CurrentTimestamp
//    private LocalDateTime creationDay;
    //todo questa sopra non va bene, non esiste a quanto pare esiste al massimo @CreationTimestamp. Per ora devo anche mettere manualmente la data
//    @CreationTimestamp
    @Column(nullable = false
//            ,updatable = false,insertable = false
            )
    private LocalDateTime creationDay;
    //membri di un gruppo
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
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
}
