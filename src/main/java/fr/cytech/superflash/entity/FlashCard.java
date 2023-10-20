package fr.cytech.superflash.entity;


import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="flashcards")
public class FlashCard {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable=false)
    private String question;

    @Column(nullable=false)
    private String reponse;

    @ManyToOne
    private Deck deck;

    @Temporal(TemporalType.DATE)
	private Date revisionTime;

    private int envelopeNb;
}
