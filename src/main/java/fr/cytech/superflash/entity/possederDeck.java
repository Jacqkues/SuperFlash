package fr.cytech.superflash.entity;

import jakarta.persistence.*;

public class possederDeck {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany
    private Deck deck;

    @ManyToMany
    private User user;

    
    
}
