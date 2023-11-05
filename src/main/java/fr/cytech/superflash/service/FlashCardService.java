package fr.cytech.superflash.service;

import java.util.List;
import java.util.Date;

import fr.cytech.superflash.dto.FlashCardDto;
import fr.cytech.superflash.entity.FlashCard;
public interface FlashCardService {

    void deleteFlashCard(Long id);
    void saveFlashCard(FlashCardDto flashCardDto);
    FlashCard findFlashCardById(Long id);
    List<FlashCard> findFlashCardByDeckId(Long id);
    FlashCard updateFlashCard(FlashCardDto flashCardDto);

    void updateRevisionTime(FlashCard flashCard, Date date);

    void update(FlashCard flashcard);
}
