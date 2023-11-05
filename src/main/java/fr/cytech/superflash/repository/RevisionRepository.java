package fr.cytech.superflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Date;
import fr.cytech.superflash.entity.Revision;
import fr.cytech.superflash.entity.User;

public interface RevisionRepository extends JpaRepository<Revision, Long>{

    List<Revision> findByUserEmailAndFinishIsFalse(String username);

    List<Revision> findByUserEmail(String username);

    @Query("SELECT r FROM Revision r WHERE r.user.email = :userName AND r.deck.id = :deckId")
    List<Revision> findAllByUserIdAndDeckId(@Param("userName") String userName, @Param("deckId") Long deckId);

    Revision findByUserAndRevisionTimeAndType(User user, Date revisionTime, String type);

    List<Revision> findByDeckIdAndFinishIsTrue(Long deckId);
} 