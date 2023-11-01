package fr.cytech.superflash.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import fr.cytech.superflash.repository.MatiereRepository;
import fr.cytech.superflash.repository.RevisionRepository;
import fr.cytech.superflash.entity.Matiere;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.entity.Revision;

@Controller
public class DashBoardController {

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private RevisionRepository revisionRepository;

    @GetMapping("/main")
    public String dash(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // User user = userRepository.findByEmail(username);
            List<Revision> revs = revisionRepository.findByUserEmailAndFinishIsFalse(username);
            model.addAttribute("revisions", revs);

        }

        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);
        return "dashboard";
    }

}
