package fr.cytech.superflash.entity;

import jakarta.persistence.*;

@Entity
public class DeckPublic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Deck deck;
    
    
}
