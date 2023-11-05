package fr.cytech.superflash.service;

import java.util.List;

import fr.cytech.superflash.dto.DeckDto;
import fr.cytech.superflash.entity.Deck;

public interface DeckService {
    Deck saveDeck(DeckDto deckDto);

    void LinkUserToDeck(String userId, Long deckId);

    void deleteDeck(Long id);

    List<Deck> findPublicDeck();

    Deck findById(Long id);

    void makePublic(Long id);

    void makePrivate(Long id);
}
