package fr.cytech.superflash.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.cytech.superflash.entity.Deck;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long>{

    List<Deck> findByIsPublic(boolean b);
    
}
