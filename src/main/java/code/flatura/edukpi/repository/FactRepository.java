package code.flatura.edukpi.repository;

import code.flatura.edukpi.model.Fact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FactRepository extends JpaRepository<Fact, Integer> {

    Optional<Fact> findById(UUID id);
}
