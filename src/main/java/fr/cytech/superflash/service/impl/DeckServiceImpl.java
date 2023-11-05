package fr.cytech.superflash.service.impl;

import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import fr.cytech.superflash.dto.DeckDto;
import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.Matiere;
import fr.cytech.superflash.entity.User;

import fr.cytech.superflash.repository.DeckRepository;
import fr.cytech.superflash.repository.MatiereRepository;

import fr.cytech.superflash.repository.UserRepository;
import fr.cytech.superflash.service.DeckService;


@Service
public class DeckServiceImpl implements DeckService{

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Deck saveDeck(DeckDto deckDto) {
        Deck deck = new Deck();
        deck.setName(deckDto.getName());
        deck.setDescription(deckDto.getDescription());
        
        Optional<Matiere > matiere = matiereRepository.findById(deckDto.getMatiereId());

        deck.setIsPublic(false);

        if(matiere.isPresent()){
            deck.setMatiere(matiere.get());
        }
        deckRepository.save(deck);
        return deck;
    }

    @Override
    public void LinkUserToDeck(String userId, Long deckId) {
        User user = userRepository.findByEmail(userId);
        Deck deck = deckRepository.findById(deckId).get();
        user.getDecks().add(deck);
        userRepository.save(user);

    }

    @Override
    public void deleteDeck(Long id) {
        Optional<Deck> deckOptional = deckRepository.findById(id);

        if (deckOptional.isEmpty()) {

            throw new RuntimeException("director not found");
        }
        Deck deck = deckOptional.orElse(new Deck());
        deckRepository.delete(deck);

    }

    @Override
    public List<Deck> findPublicDeck() {
        return deckRepository.findByIsPublic(true);
    }
    
    @Override
    public Deck findById(Long id) {
        Optional<Deck> deckOptional = deckRepository.findById(id);
        if (deckOptional.isEmpty()) {
            throw new RuntimeException("deck not found");
        }
        Deck deck = deckOptional.orElse(new Deck());
        return deck;
    }

    @Override
    public void makePublic(Long id) {
        Optional<Deck> deckOptional = deckRepository.findById(id);

        if (deckOptional.isEmpty()) {

            throw new RuntimeException("deck not found");
        }
        Deck deck = deckOptional.orElse(new Deck());
        deck.setIsPublic(true);
        deckRepository.save(deck);
    }

    @Override
    public void makePrivate(Long id) {
        Optional<Deck> deckOptional = deckRepository.findById(id);

        if (deckOptional.isEmpty()) {

            throw new RuntimeException("deck not found");
        }
        Deck deck = deckOptional.orElse(new Deck());
        deck.setIsPublic(false);
        deckRepository.save(deck);
    }

    @Override
    public List<Deck> selectPublicDeckByMatiere(Long id) {
        return deckRepository.findByMatiereIdAndIsPublicIsTrue(id);
    }
    
}
