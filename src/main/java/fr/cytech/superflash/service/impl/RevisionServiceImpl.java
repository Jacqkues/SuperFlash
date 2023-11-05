package fr.cytech.superflash.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.cytech.superflash.entity.Revision;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.FlashCard;
import fr.cytech.superflash.repository.RevisionRepository;
import fr.cytech.superflash.repository.DeckRepository;
import fr.cytech.superflash.service.RevisionService;

import fr.cytech.superflash.repository.UserRepository;

import fr.cytech.superflash.service.FlashCardService;
import fr.cytech.superflash.repository.FlashCardRepository;

import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.text.SimpleDateFormat;

@Service
public class RevisionServiceImpl implements RevisionService {

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlashCardService flashCardService;

    @Autowired
    private FlashCardRepository flashCardRepository;

    @Override
    public List<Revision> findNotFinishedRevision(String userId) {
        return revisionRepository.findByUserEmailAndFinishIsFalse(userId);
    }

    @Override
    public Revision newSmartRevision(String userName, Date date) {
        // get the current date
        Date currentDate = new Date();
        User user = userRepository.findByEmail(userName);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

       
        String formattedDate = sdf.format(currentDate);

        Revision rev = revisionRepository.findByUserAndRevisionTimeAndType(user, currentDate, "smart");

        if (rev != null) {
            return rev;
        } else {
            rev = new Revision();
            rev.setFinish(false);
            rev.setLastIndex(0);
            Deck deck = new Deck();
            deck.setName("Revision : " + formattedDate);
            deck.setDescription("Smart revision for the day : " + formattedDate);
            deckRepository.save(deck);
            rev.setDeck(deck);

            List<FlashCard> flashcards = flashCardRepository.findByRevisionTime(currentDate);


            if(flashcards.isEmpty()){
                return null;
            }

            Collections.shuffle(flashcards);
            rev.setFlashcards(flashcards);
            rev.setNbFlashCard(flashcards.size());
            rev.setNbFlashCardGood(0);
            rev.setUser(user);
            rev.setRevisionTime(currentDate);
            rev.setType("smart");
            revisionRepository.save(rev);

            return rev;
        }
    }

    @Override
    public Revision newDeckRevision(Long deckId, String userName) {
        Revision rev = new Revision();
        rev.setType("deck");
        Deck deck = deckRepository.findById(deckId).get();

        rev.setDeck(deck);

        rev.setFinish(false);
        rev.setLastIndex(0);

        List<FlashCard> flashcards = flashCardService.findFlashCardByDeckId(deckId);
        


        rev.setNbFlashCard(flashcards.size());
        rev.setNbFlashCardGood(0);
        Date currentDate = new Date();
        rev.setRevisionTime(currentDate);
        Collections.shuffle(flashcards);
        rev.setFlashcards(flashcards);

        User user = userRepository.findByEmail(userName);
        rev.setUser(user);

        revisionRepository.save(rev);
        return rev;

    }

    @Override
    public void incrRevisionIndex(Revision revision, int index) {
        revision.setLastIndex(index + 1);
        revisionRepository.save(revision);
    }

    @Override
    public Revision findById(Long id) {
        Optional<Revision> revOptional = revisionRepository.findById(id);
        if (revOptional.isEmpty()) {
            throw new RuntimeException("revision not found");
        }
        Revision rev = revOptional.orElse(new Revision());
        return rev;
    }

    @Override
    public void update(Revision revision) {
        revisionRepository.save(revision);
    }

    @Override
    public List<Revision> findFinishedRevision(String userId) {
        //return revisionRepository.findByUserEmailAndFinishIsFalse(userId);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Revision> findAllByUserAndDeck(String userId, Long deckId) {
        return revisionRepository.findAllByUserIdAndDeckId(userId, deckId);
    }

    @Override
    public List<Revision> findFinishedRevisionsByDeck(Long deckId) {
        return revisionRepository.findByDeckIdAndFinishIsTrue(deckId);
    }

}
