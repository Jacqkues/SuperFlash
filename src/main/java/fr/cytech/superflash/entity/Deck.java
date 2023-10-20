package fr.cytech.superflash.entity;


import jakarta.persistence.*;
@Entity
@Table(name = "deck")
public class Deck {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String description;

    @ManyToOne
    private Matiere matiere;

}
