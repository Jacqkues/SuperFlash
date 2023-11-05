package fr.cytech.superflash.controller;

import java.util.List;

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
import fr.cytech.superflash.repository.MatiereRepository;

import fr.cytech.superflash.service.DeckService;
import fr.cytech.superflash.service.FlashCardService;
import fr.cytech.superflash.service.UserService;

@Controller
public class DeckController {

    @Autowired
    private DeckService deckService;

    @Autowired
    private FlashCardService flashCardService;

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/main/deck/explore")
    public String explorePublicDeck(Model model) {
        List<Deck> decks = deckService.findPublicDeck();

        model.addAttribute("decks", decks);
        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);
        return "explore";

    }

    @GetMapping("/main/deck/explore/{id}")
    public String explorePublicDeckFilter(Model model, @PathVariable Long id) {
        List<Deck> decks = deckService.selectPublicDeckByMatiere(id);

        model.addAttribute("decks", decks);
        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);
        return "explore";

    }

    @PostMapping("/main/deck/publish")
    public String publishDeck(Long deckId) {

        Deck deck = deckService.findById(deckId);
        if (deck.isIsPublic()) {
            deckService.makePrivate(deckId);
        } else {
            deckService.makePublic(deckId);
        }

        return "redirect:/main/deck/edit/" + deck.getId();
    }

    @PostMapping("/main/deck/save")
    public String saveDeck(@Valid @ModelAttribute("deck") DeckDto deckDto, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("deck", deckDto);
            return "redirect:/main/deck?error";
        }
        Deck deck = deckService.saveDeck(deckDto);

        User user = userService.getAuthUser();
        deckService.LinkUserToDeck(user.getEmail(), deck.getId());
        userService.updateNbDeckCree(user);
        return "redirect:/main/deck/edit/" + deck.getId();
    }

    @GetMapping("/main/deck/edit/{id}")
    public String flashcard(@PathVariable Long id, Model model) {

        Deck deck = deckService.findById(id);
        model.addAttribute("deck", deck);

        List<FlashCard> flashCards = flashCardService.findFlashCardByDeckId(id);
        model.addAttribute("flashcards", flashCards);

        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);
        return "editdeck";
    }

    @GetMapping("/main/deck")
    public String dash(Model model) {

        List<Deck> decks = userService.getAuthUser().getDecks();
        model.addAttribute("decks", decks);
        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);

        return "deck";
    }

    @GetMapping("/main/deck/delete/{id}")
    public String deleteDeck(@PathVariable Long id) {

        User user = userService.getAuthUser();
        user.getDecks().removeIf(deck -> deck.getId() == id);
        userService.updateUser(user);
        deckService.makePrivate(id);

        return "redirect:/main/deck";
    }

}
