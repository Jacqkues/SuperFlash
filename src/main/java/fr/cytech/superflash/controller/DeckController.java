package fr.cytech.superflash.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import fr.cytech.superflash.dto.DeckDto;
import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.FlashCard;
import fr.cytech.superflash.entity.Matiere;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.repository.DeckRepository;
import fr.cytech.superflash.repository.FlashCardRepository;
import fr.cytech.superflash.repository.MatiereRepository;
import fr.cytech.superflash.repository.UserRepository;
import fr.cytech.superflash.service.DeckService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@Controller
public class DeckController {

    @Autowired
    private DeckService deckService;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private FlashCardRepository flashCardRepository;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/main/deck/save")
    public String saveDeck(@Valid @ModelAttribute("deck") DeckDto deckDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("deck", deckDto);
            return "dashboard";
        }
        Deck deck = deckService.saveDeck(deckDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();
            
            deckService.LinkUserToDeck(username, deck.getId());
            
        }

        return "redirect:/main/deck/edit/" + deck.getId();
    }

    @GetMapping("/main/deck/edit/{id}")
    public String flashcard(@PathVariable Long id, Model model) {

        Optional<Deck> deckOptional = deckRepository.findById(id);

        if (deckOptional.isEmpty()) {

            throw new RuntimeException("director not found");
        }
        Deck deck = deckOptional.orElse(new Deck());
        model.addAttribute("deck", deck);

        List<FlashCard> flashCards = flashCardRepository.findByDeckId(id);
        model.addAttribute("flashcards", flashCards);

        return "editdeck";
    }

    @GetMapping("/main/deck")
    public String dash(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();


            User user = userRepository.findByEmail(username);

            List<Deck> decks = user.getDecks();
            model.addAttribute("decks", decks); 
        }
        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);

        return "deck";
    }

    @GetMapping("/main/deck/delete/{id}")
    public String deleteDeck(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();


            User user = userRepository.findByEmail(username);

            user.getDecks().removeIf(deck -> deck.getId() == id);
            userRepository.save(user);
           // model.addAttribute("decks", decks); 
        }
       // deckService.deleteDeck(id);

        return "redirect:/main/deck";
    }

}
