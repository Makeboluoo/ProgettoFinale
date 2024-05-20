package it.polimi.zagardo.progettofinale.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.boot.model.naming.Identifier;

@Table(uniqueConstraints = { @UniqueConstraint(name = "user_event_unique_constraint", columnNames = { "user_id", "event_id" }) })
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",nullable = false)
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "event_id",nullable = false)
    private Event event;

    public Comment(String text, UserModel user, Event event) {
        this.text = text;
        this.user = user;
        this.event = event;
    }
}
