package fr.cytech.superflash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import fr.cytech.superflash.dto.DeckDto;
import fr.cytech.superflash.dto.FlashCardDto;
import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.FlashCard;
import fr.cytech.superflash.repository.DeckRepository;
import fr.cytech.superflash.repository.FlashCardRepository;
import fr.cytech.superflash.service.FlashCardService;
import jakarta.validation.Valid;

import java.util.Optional;

import java.util.List;

@Controller
public class FlashCardController {

    @Autowired
    private FlashCardRepository flashCardRepository;

    @Autowired
    private FlashCardService flashCardService;

    @PostMapping("/main/flashcard/answer/{id}")
    public String answerFlashCard(@PathVariable Long id, Model model) {
        FlashCard flashCard = flashCardService.findFlashCardById(id);
        //flashCard.setEnvelopeNb(flashCard.getEnvelopeNb() + 1);
        //flashCardRepository.save(flashCard);
        model.addAttribute("flashcard", flashCard);
        return "redirect:/main/deck/edit/" + flashCard.getDeck().getId() + "?success";
    }


    @GetMapping("/main/flashcard/answer/{id}")
    public String pageAnswerFlashCard(@PathVariable Long id , Model model){

       



        List<FlashCard> flashcards = flashCardService.findFlashCardByDeckId(id);
        model.addAttribute("flashcards", flashcards);
        return "reponse";
    }


    @PostMapping("/main/flashcard/update")
    public String updateFlashCard(@Valid @ModelAttribute("flashcard") FlashCardDto flashCardDto, BindingResult result,
            Model model) {


        if (result.hasErrors()) {
            model.addAttribute("flashcard", flashCardDto);
            return "redirect:/main/deck/edit/" + flashCardDto.getDeckId();
        }
        FlashCard flash = flashCardService.updateFlashCard(flashCardDto);

        return "redirect:/main/deck/edit/" + flash.getDeck().getId() + "?success";

    }

    @PostMapping("/main/flashcard/save")
    public String saveFlashCard(@Valid @ModelAttribute("flashcard") FlashCardDto flashCardDto, BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("flashcard", flashCardDto);
            return "redirect:/main/deck/edit/" + flashCardDto.getDeckId();
        }
        flashCardService.saveFlashCard(flashCardDto);
        return "redirect:/main/deck/edit/" + flashCardDto.getDeckId() + "?success";

    }

    @GetMapping("/main/flashcard/delete/{deckId}/{id}")
    public String deleteFlashCard(@PathVariable Long deckId, @PathVariable Long id , Model model) {
        flashCardService.deleteFlashCard(id);
        model.addAttribute("deckId", deckId);
        return "redirect:/main/deck/edit/" + deckId + "?success";
    }

    @GetMapping("/main/flashcard/edit/{id}")
    public String editFlashCard(@PathVariable Long id, Model model) {
        FlashCard flashCard = flashCardService.findFlashCardById(id);

        model.addAttribute("flashcard", flashCard);

        return "flashcardedit";
    }

}
