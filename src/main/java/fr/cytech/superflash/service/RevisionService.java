package fr.cytech.superflash.service;


import fr.cytech.superflash.entity.Revision;
import java.util.List;

import java.util.List;

public interface RevisionService {
    List<Revision> findNotFinishedRevision(String userId);
}
