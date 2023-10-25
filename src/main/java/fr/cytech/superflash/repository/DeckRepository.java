package fr.cytech.superflash.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import fr.cytech.superflash.entity.Deck;

public interface DeckRepository extends JpaRepository<Deck, Long>{
    
}
