package fr.cytech.superflash.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cytech.superflash.dto.FlashCardDto;
import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.FlashCard;
import fr.cytech.superflash.repository.DeckRepository;
import fr.cytech.superflash.repository.FlashCardRepository;
import fr.cytech.superflash.service.FlashCardService;

@Service
public class FlashCardServiceImpl implements FlashCardService {

    @Autowired
    private FlashCardRepository flashCardRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Override
    public void deleteFlashCard(Long id) {
        flashCardRepository.deleteById(id);
    }

    @Override
    public void saveFlashCard(FlashCardDto flashCardDto) {

        Date currentDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.add(Calendar.DAY_OF_MONTH, 1);

        FlashCard flashCard = new FlashCard();
        flashCard.setQuestion(flashCardDto.getQuestion());
        flashCard.setReponse(flashCardDto.getReponse());
        flashCard.setEnvelopeNb(1);
        flashCard.setRevisionTime(calendar.getTime());

        Optional<Deck> deck = deckRepository.findById(flashCardDto.getDeckId());

        if (deck.isPresent()) {
            flashCard.setDeck(deck.get());
        }

        flashCardRepository.save(flashCard);

    }

    @Override
    public FlashCard findFlashCardById(Long id) {

        Optional<FlashCard> flashCard = flashCardRepository.findById(id);

        if (flashCard.isPresent()) {
            return flashCard.get();
        }

        return null;
    }

    @Override
    public List<FlashCard> findFlashCardByDeckId(Long id) {
        List<FlashCard> flashCards = flashCardRepository.findByDeckId(id);
        return flashCards;
    }

    @Override
    public FlashCard updateFlashCard(FlashCardDto flashCardDto) {
            
            Optional<FlashCard> flashCard = flashCardRepository.findById(flashCardDto.getId());
    
            if (flashCard.isPresent()) {
                FlashCard finalFlashCard = flashCard.get();
                finalFlashCard.setQuestion(flashCardDto.getQuestion());
                finalFlashCard.setReponse(flashCardDto.getReponse());
                flashCardRepository.save(finalFlashCard);

                return finalFlashCard;
            }

            return null;
            
    }

    @Override
    public void updateRevisionTime(FlashCard flashCard, Date date){
        flashCard.setRevisionTime(date);
        flashCardRepository.save(flashCard);
    }

    @Override
    public void update(FlashCard flashcard){
        flashCardRepository.save(flashcard);
    }
}
