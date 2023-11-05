package fr.cytech.superflash.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import fr.cytech.superflash.entity.FlashCard;
import fr.cytech.superflash.entity.Revision;
import fr.cytech.superflash.entity.User;

import fr.cytech.superflash.service.FlashCardService;
import fr.cytech.superflash.service.RevisionService;
import fr.cytech.superflash.service.UserService;


import java.util.Calendar;
import java.util.List;

import java.util.Date;


@Controller
public class RevisionController {

    @Autowired
    private FlashCardService flashCardService;

    @Autowired
    private RevisionService revisionService;

    @Autowired
    private UserService userService;

    @GetMapping("/main/newsmart")
    public String newSmartRev(Model model) {
        Date currentDate = new Date();
        User user = userService.getAuthUser();
        Revision rev = revisionService.newSmartRevision(user.getEmail(), currentDate);
        return "redirect:/main/flashcard/revision/" + rev.getId() + "/0";
    }

    @PostMapping("/main/flashcard/revision/deck")
    public String revisionFlashCard(Long id, Model model) {
        User user = userService.getAuthUser();
        Revision rev = revisionService.newDeckRevision(id, user.getEmail());
        return "redirect:/main/flashcard/revision/" + rev.getId() + "/0";
    }

    @PostMapping("/main/flashcard/revision/{id}/{index}")
    public String answerFlashCard(@PathVariable Long id, String reponse, @PathVariable int index, Model model) {

        Revision rev = revisionService.findById(id);

        List<FlashCard> flashcards = rev.getFlashcards();
        if (index >= flashcards.size()) {
            return "redirect:/main/flashcard/revision/result/" + rev.getId();
        }
        FlashCard flashcard = flashcards.get(index);

        Date revDate = flashcard.getRevisionTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(revDate);

        if (flashcard.getReponse().equals(reponse)) {

            userService.increaseScore();

            rev.setNbFlashCardGood(rev.getNbFlashCardGood() + 1);

            if (rev.getType().equals("smart")) {

                switch (flashcard.getEnvelopeNb()) {
                    case 1:
                        calendar.add(Calendar.DAY_OF_MONTH, 2);
                        flashcard.setEnvelopeNb(2);
                        break;
                    case 2:
                        calendar.add(Calendar.DAY_OF_MONTH, 3);
                        flashcard.setEnvelopeNb(3);
                        break;
                    case 3:
                        flashcard.setEnvelopeNb(4);
                        calendar.add(Calendar.DAY_OF_MONTH, 4);
                    case 4:
                        calendar.add(Calendar.MONTH, 1);
                        break;
                    default:
                        break;
                }
            }
            flashCardService.updateRevisionTime(flashcard, calendar.getTime());

        } else if (!flashcard.getReponse().equals(reponse) && rev.getType().equals("smart")) {
            flashcard.setEnvelopeNb(1);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            flashcard.setRevisionTime(calendar.getTime());
            flashCardService.update(flashcard);
            userService.decreaseScore();
        } else {
            userService.decreaseScore();
        }

        revisionService.incrRevisionIndex(rev, index);
        model.addAttribute("flashcard", flashcard);
        model.addAttribute("index", index);
        model.addAttribute("revision", rev);
        return "redirect:/main/flashcard/revision/" + id + "/" + (index + 1);
    }

    @GetMapping("/main/flashcard/revision/{id}/{index}")
    public String pageRevisionFlashCard(@PathVariable Long id, @PathVariable int index, Model model) {

        Revision rev = revisionService.findById(id);
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

        Revision rev = revisionService.findById(id);

      
        rev.setFinish(true);
        revisionService.update(rev);

        List<Revision> revs = revisionService.findAllByUserAndDeck(rev.getUser().getEmail(), rev.getDeck().getId());
        model.addAttribute("revisions", revs);
        model.addAttribute("revision", rev);
        model.addAttribute("pourcentage", rev.getPercentage());
        return "result";
    }

}
