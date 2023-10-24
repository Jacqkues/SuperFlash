package fr.cytech.superflash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.cytech.superflash.repository.MatiereRepository;
import fr.cytech.superflash.entity.Matiere;

@Controller
public class DashBoardController {




    @Autowired
    private MatiereRepository matiereRepository;

    @GetMapping("/main")
    public String dash(Model model) {

        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);
        return "adddeck";
    }

    
}
