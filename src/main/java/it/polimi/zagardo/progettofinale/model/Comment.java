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
    //persona che ha scritto il commento (sotto forma di iscrizione)
    //todo tolto la eager perché messa in groupRight
    @ManyToOne(/*fetch = FetchType.EAGER*/)
    @JoinColumn(name = "groupRight_id")
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
