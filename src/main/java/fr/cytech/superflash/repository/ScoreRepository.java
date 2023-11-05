package fr.cytech.superflash.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import fr.cytech.superflash.entity.Score;

public interface ScoreRepository extends JpaRepository<Score, Long>{

    
}
