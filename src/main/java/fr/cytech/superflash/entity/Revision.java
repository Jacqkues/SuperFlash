package fr.cytech.superflash.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;

@Entity
public class Revision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(name = "revision_flashcard", joinColumns = @JoinColumn(name = "revision_id"), inverseJoinColumns = @JoinColumn(name = "flashcard_id"))
    private List<FlashCard> flashcards = new ArrayList<>();

    @Column(nullable = false)
    private int nbFlashCard;

    @Column(nullable = false)
    private int nbFlashCardGood;

    @Temporal(TemporalType.DATE)
    private Date revisionTime;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = true)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    private String type;

    private boolean finish;

    private int lastIndex;

    public int getLastIndex(){
        return this.lastIndex;
    }

    public void setLastIndex(int index){
        this.lastIndex = index;
    }
    
    public void setFinish(boolean finish){
        this.finish = finish;
    }

    public boolean getFinish(){
        return this.finish;
    }

    public String getType(){
        return this.type;
    }

    public void setType(String type){
        this.type = type;
    }

    public Deck getDeck(){
        return this.deck;
    }


    public void setDeck(Deck deck){
        this.deck = deck;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getRevisionTime() {
        return revisionTime;
    }

    public void setRevisionTime(Date revisionTime) {
        this.revisionTime = revisionTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FlashCard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(List<FlashCard> flashcards) {
        this.flashcards = flashcards;
    }

    public int getNbFlashCard() {
        return nbFlashCard;
    }

    public void setNbFlashCard(int nbFlashCard) {
        this.nbFlashCard = nbFlashCard;
    }

    public int getNbFlashCardGood() {
        return nbFlashCardGood;
    }

    public void setNbFlashCardGood(int nbFlashCardGood) {
        this.nbFlashCardGood = nbFlashCardGood;
    }
}
