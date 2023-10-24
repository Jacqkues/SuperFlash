package fr.cytech.superflash.repository;

import fr.cytech.superflash.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByDeckId(Long deckId);
}
