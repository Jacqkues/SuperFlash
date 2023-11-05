package fr.cytech.superflash.service;


import fr.cytech.superflash.entity.Revision;
import java.util.List;

import java.util.Date;

public interface RevisionService {
    List<Revision> findNotFinishedRevision(String userId);

    Revision newDeckRevision(Long deckId, String userName);

    Revision newSmartRevision(String userName , Date date);

    void incrRevisionIndex(Revision revision , int index);

    Revision findById(Long id);

    void update(Revision revision);

    List<Revision> findFinishedRevision(String userId);

    List<Revision> findFinishedRevisionsByDeck(Long deckId);

    List<Revision> findAllByUserAndDeck(String userId, Long deckId);
}
