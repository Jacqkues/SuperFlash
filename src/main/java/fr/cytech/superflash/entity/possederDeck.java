package fr.cytech.superflash.entity;

import jakarta.persistence.*;

@Entity
public class possederDeck {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
	@JoinColumn(name = "deck_id")
    private Deck deck;

    @OneToOne
	@JoinColumn(name = "user_id")
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    
    
}
