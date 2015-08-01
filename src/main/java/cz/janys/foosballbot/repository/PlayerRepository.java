package cz.janys.foosballbot.repository;

import cz.janys.foosballbot.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Martin Janys
 */
@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    PlayerEntity findByJabber(String jabber);
}
