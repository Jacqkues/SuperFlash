package fr.cytech.superflash.repository;

import fr.cytech.superflash.entity.FlashCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Date;

public interface FlashCardRepository extends JpaRepository<FlashCard, Long> {
    List<FlashCard> findByDeckId(Long deckId);

    List<FlashCard> findByRevisionTime(Date date);
}
