package fr.cytech.superflash.repository;


import fr.cytech.superflash.entity.possederDeck;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PossederDeckRepository extends JpaRepository<possederDeck, Long> {
    
}
