package fr.cytech.superflash.controller;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;



import fr.cytech.superflash.repository.MatiereRepository;

import fr.cytech.superflash.service.RevisionService;
import fr.cytech.superflash.service.UserService;
import fr.cytech.superflash.entity.Matiere;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.entity.Revision;

@Controller
public class DashBoardController {

    @Autowired
    private MatiereRepository matiereRepository;

    @Autowired
    private RevisionService revisionService;

    @Autowired
    private UserService userService;

    @GetMapping("/main")
    public String dash(Model model) {

        User user = userService.getAuthUser();
        String username = user.getEmail();

        List<Revision> revs = revisionService.findNotFinishedRevision(username);

        model.addAttribute("revisions", revs);
        Date currentDate = new Date();
        Revision smartRev = revisionService.newSmartRevision(username, currentDate);
        model.addAttribute("smartRev", smartRev);

        List<Matiere> matieres = matiereRepository.findAll();
        model.addAttribute("matieres", matieres);
        return "dashboard";
    }

}
