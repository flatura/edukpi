package code.flatura.edukpi.repository;

import code.flatura.edukpi.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

    Optional<Position> findById(UUID id);
}
