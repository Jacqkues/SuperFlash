package fr.cytech.superflash.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import fr.cytech.superflash.entity.Revision;
import fr.cytech.superflash.repository.RevisionRepository;
import fr.cytech.superflash.service.RevisionService;

import java.util.List;

public class RevisionServiceImpl implements RevisionService {


    @Autowired
    private RevisionRepository revisionRepository;


    @Override
    public List<Revision> findNotFinishedRevision(String userId) {
        return revisionRepository.findByUserEmailAndFinishIsFalse(userId);
    }
    
}
