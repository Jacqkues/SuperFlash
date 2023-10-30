package fr.cytech.superflash.entity;

import java.util.Date;
import jakarta.persistence.*;

@Entity
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    private Deck deck;

    private int score;

}
