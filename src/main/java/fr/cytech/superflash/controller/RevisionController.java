package fr.cytech.superflash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.FlashCard;
import fr.cytech.superflash.entity.Revision;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.repository.DeckRepository;
import fr.cytech.superflash.repository.RevisionRepository;
import fr.cytech.superflash.repository.UserRepository;
import fr.cytech.superflash.service.FlashCardService;

import java.util.Optional;

import java.util.List;
import java.util.Collections;
import java.util.Date;
import java.util.ArrayList;

@Controller
public class RevisionController {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private FlashCardService flashCardService;

    @Autowired
    private RevisionRepository revisionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/main/flashcard/revision/deck")
    public String revisionFlashCard(Long id, Model model) {

        Revision rev = new Revision();

        rev.setType("deck");

        Deck deck = deckRepository.findById(id).get();

        rev.setDeck(deck);

        rev.setFinish(false);
        rev.setLastIndex(0);

        List<FlashCard> flashcards = flashCardService.findFlashCardByDeckId(id);
        rev.setNbFlashCard(flashcards.size());
        rev.setNbFlashCardGood(0);
        Date currentDate = new Date();
        rev.setRevisionTime(currentDate);
        Collections.shuffle(flashcards);
        rev.setFlashcards(flashcards);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            User user = userRepository.findByEmail(username);
            rev.setUser(user);

        }

        revisionRepository.save(rev);

        return "redirect:/main/flashcard/revision/" + rev.getId() + "/0";
    }

    @PostMapping("/main/flashcard/revision/{id}/{index}")
    public String answerFlashCard(@PathVariable Long id, String reponse, @PathVariable int index, Model model) {

        Optional<Revision> revOptional = revisionRepository.findById(id);
        if (revOptional.isEmpty()) {
            throw new RuntimeException("revision not found");
        }
        Revision rev = revOptional.orElse(new Revision());
        List<FlashCard> flashcards = rev.getFlashcards();
        if (index >= flashcards.size()) {
            return "redirect:/main/flashcard/revision/result/" + rev.getId();
        }
        FlashCard flashcard = flashcards.get(index);

        if (flashcard.getReponse().equals(reponse)) {
            rev.setNbFlashCardGood(rev.getNbFlashCardGood() + 1);

        }
        rev.setLastIndex(index + 1);
        revisionRepository.save(rev);
        model.addAttribute("flashcard", flashcard);
        model.addAttribute("index", index);
        model.addAttribute("revision", rev);
        return "redirect:/main/flashcard/revision/" + id + "/" + (index + 1);
    }

    @GetMapping("/main/flashcard/revision/{id}/{index}")
    public String pageRevisionFlashCard(@PathVariable Long id, @PathVariable int index, Model model) {
        Optional<Revision> revOptional = revisionRepository.findById(id);
        if (revOptional.isEmpty()) {
            throw new RuntimeException("revision not found");
        }
        Revision rev = revOptional.orElse(new Revision());
        List<FlashCard> flashcards = rev.getFlashcards();
        if (index >= flashcards.size()) {
            return "redirect:/main/flashcard/revision/result/" + rev.getId();
        }
        FlashCard flashcard = flashcards.get(index);
        model.addAttribute("flashcard", flashcard);
        model.addAttribute("index", index);
        model.addAttribute("revision", rev);
        return "answerFlashCard";
    }

    @GetMapping("/main/flashcard/revision/result/{id}")
    public String pageRevisionResult(@PathVariable Long id, Model model) {
        Optional<Revision> revOptional = revisionRepository.findById(id);
        if (revOptional.isEmpty()) {
            throw new RuntimeException("revision not found");
        }
        Revision rev = revOptional.orElse(new Revision());

        // calculate the pourcentage of good answer
        int pourcentage = (int) (((double) rev.getNbFlashCardGood() / (double) rev.getNbFlashCard()) * 100);

        rev.setFinish(true);
        revisionRepository.save(rev);

        List<Revision> revs = revisionRepository.findAllByUserIdAndDeckId(rev.getUser().getEmail(),
                rev.getDeck().getId());
        model.addAttribute("revisions", revs);
        model.addAttribute("revision", rev);
        model.addAttribute("pourcentage", pourcentage);
        return "result";
    }

}
