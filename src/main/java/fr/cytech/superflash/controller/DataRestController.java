package fr.cytech.superflash.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import fr.cytech.superflash.Javaclass.ChartData;
import fr.cytech.superflash.Javaclass.RandomColor;
import fr.cytech.superflash.service.RevisionService;
import fr.cytech.superflash.service.UserService;
import fr.cytech.superflash.entity.Deck;
import fr.cytech.superflash.entity.User;
import fr.cytech.superflash.entity.Revision;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@RestController
public class DataRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private RevisionService revisionService;


    @GetMapping("/main/api/progress/deck/{dekcId}")
    public Map<String, Object> getDeckData(@PathVariable Long deckId) {

        Map<String, Object> data = new HashMap<>();

       // User user = userService.getAuthUser();

        List<Revision> finishedRevisions = revisionService.findFinishedRevisionsByDeck(deckId);
        if (!finishedRevisions.isEmpty()) {
            List<Integer> percentages = new ArrayList<>();

            for (Revision revision : finishedRevisions) {
                percentages.add(revision.getPercentage());
            }

            data.put("name", finishedRevisions.get(0).getDeck().getName());
            data.put("data", percentages.stream().mapToInt(Integer::intValue).toArray());
            data.put("color", RandomColor.generateRandomColor());

            
        }

        return data;
    }


    @GetMapping("/main/api/progress")
    public List<ChartData> getData() {
        List<ChartData> data = new ArrayList<>();

        User user = userService.getAuthUser();

        List<Deck> decks = user.getDecks();

        for (Deck deck : decks) {
            List<Revision> finishedRevisions = revisionService.findFinishedRevisionsByDeck(deck.getId());
            if (!finishedRevisions.isEmpty()) {
                List<Integer> percentages = new ArrayList<>();

                for (Revision revision : finishedRevisions) {
                    percentages.add(revision.getPercentage());
                }

                ChartData chartData = new ChartData(deck.getName(),
                        percentages.stream().mapToInt(Integer::intValue).toArray(), RandomColor.generateRandomColor());
                data.add(chartData);
            }

        }

        // Add data points to the list

        return data;
    }

}
