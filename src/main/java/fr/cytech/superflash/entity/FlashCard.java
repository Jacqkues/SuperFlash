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

    @ManyToOne()
	@JoinColumn(name = "deck_id")
    private Deck deck;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public Date getRevisionTime() {
		return revisionTime;
	}

	public void setRevisionTime(Date revisionTime) {
		this.revisionTime = revisionTime;
	}

	public int getEnvelopeNb() {
		return envelopeNb;
	}

	public void setEnvelopeNb(int envelopeNb) {
		this.envelopeNb = envelopeNb;
	}

	@Temporal(TemporalType.DATE)
	private Date revisionTime;

    private int envelopeNb;
}
