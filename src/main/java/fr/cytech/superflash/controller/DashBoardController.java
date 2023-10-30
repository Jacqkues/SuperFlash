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
import org.springframework.web.bind.annotation.RequestMapping;

import fr.cytech.superflash.repository.MatiereRepository;
import fr.cytech.superflash.entity.Matiere;

import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.repository.DeckRepository;

@Controller
public class DashBoardController {

    @Autowired
    private MatiereRepository matiereRepository;
    private DeckRepository deckRepository;

    @GetMapping("/main")
    public String dash(Model model) {

        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);
        List<Deck> decks = deckRepository.findAll();
        model.addAttribute("decks",decks);
        return "dashboard";
    }

  

}
