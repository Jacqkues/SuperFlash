package fr.cytech.superflash.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import fr.cytech.superflash.dto.DeckDto;
import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.Matiere;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.entity.possederDeck;
import fr.cytech.superflash.repository.DeckRepository;
import fr.cytech.superflash.repository.MatiereRepository;
import fr.cytech.superflash.repository.PossederDeckRepository;
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

    @Autowired 
    private PossederDeckRepository possederDeckRepository;



    @Override
    public Deck saveDeck(DeckDto deckDto) {
        Deck deck = new Deck();
        deck.setName(deckDto.getName());
        deck.setDescription(deckDto.getDescription());
        
        Optional<Matiere > matiere = matiereRepository.findById(deckDto.getMatiereId());

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

    
    
}
