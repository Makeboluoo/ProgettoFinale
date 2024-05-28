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
    //testo del commento
    @Column(nullable = false)
    private String text;
    //groupRight del creatore nel gruppo dell'evento su cui è postato un commento
    //todo stessa questione del delete dell'evento: devo togliere il carattere nullable false
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupRight_id"/*,nullable = false*/)
    private GroupRights groupRights;
    //evento su cui è postato un commento
    @ManyToOne
    @JoinColumn(name = "event_id"/*,nullable = false*/)
    private Event event;

    public Comment(String text, GroupRights groupRights, Event event) {
        this.text = text;
        this.groupRights = groupRights;
        this.event = event;
    }
}
