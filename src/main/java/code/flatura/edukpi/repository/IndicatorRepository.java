package code.flatura.edukpi.repository;

import code.flatura.edukpi.model.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, Integer> {

    Optional<Indicator> findById(UUID id);
}
