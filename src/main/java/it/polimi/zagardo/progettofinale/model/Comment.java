package it.polimi.zagardo.progettofinale.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.model.naming.Identifier;

//@Table(uniqueConstraints = { @UniqueConstraint(name = "user_event_unique_constraint", columnNames = { "user_id", "event_id" }) })
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String text;
    //todo stessa questione del delete dell'evento: devo togliere il carattere nullable false
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupRight_id"/*,nullable = false*/)
    private GroupRights groupRights;
    @ManyToOne
    @JoinColumn(name = "event_id"/*,nullable = false*/)
    private Event event;

    //todo vogliamo aggiungere anche la data così posso passarglieli ordinati per data??

    public Comment(String text, GroupRights groupRights, Event event) {
        this.text = text;
        this.groupRights = groupRights;
        this.event = event;
    }
}
