package fr.cytech.superflash.service;

import fr.cytech.superflash.dto.DeckDto;
import fr.cytech.superflash.entity.Deck;

public interface DeckService {
    Deck saveDeck(DeckDto deckDto);

    void LinkUserToDeck(String userId, Long deckId);
}
