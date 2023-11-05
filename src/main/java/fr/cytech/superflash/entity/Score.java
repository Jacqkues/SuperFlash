package fr.cytech.superflash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Id;


@Entity
public class Score {
    

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    @OneToOne
    private User user;
    
    private int nbDeckCree;

    private int score;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNbDeckCree() {
        return nbDeckCree;
    }

    public void setNbDeckCree(int nbDeckCree) {
        this.nbDeckCree = nbDeckCree;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
