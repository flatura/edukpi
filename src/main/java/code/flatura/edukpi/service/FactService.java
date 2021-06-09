package code.flatura.edukpi.service;

import code.flatura.edukpi.model.DTO.FactDto;
import code.flatura.edukpi.model.Fact;
import code.flatura.edukpi.model.Indicator;
import code.flatura.edukpi.model.User;
import code.flatura.edukpi.repository.FactRepository;
import code.flatura.edukpi.repository.IndicatorRepository;
import code.flatura.edukpi.repository.UserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class FactService {

    private FactRepository factRepository;

    private IndicatorRepository indicatorRepository;

    private UserRepository userRepository;

    public FactService(FactRepository factRepository, IndicatorRepository indicatorRepository, UserRepository userRepository) {
        this.factRepository = factRepository;
        this.indicatorRepository = indicatorRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Modifying
    public Fact update(UUID id, FactDto factDto) {
        UUID employeeId, indicatorId;
        Fact result = null;
        try {
            employeeId = UUID.fromString(factDto.getEmployeeId());
            indicatorId = UUID.fromString(factDto.getIndicatorId());
            Optional<Indicator> optIndicator = indicatorRepository.findById(indicatorId);
            Optional<User> optUser = userRepository.findById(employeeId);
            if (optIndicator.isPresent() && optUser.isPresent()) {
                if (id != null) {
                    // Update existing entity
                    Optional optFact = factRepository.findById(id);
                    if (optFact.isPresent()) {
                        result = (Fact) optFact.get();
                        result.setIndicator(optIndicator.get());
                        result.setUser(optUser.get());
                        result.setPointsSuggested(factDto.getPointsSuggested());
                        result.setPointsApproved(factDto.getPointsApproved());
                        result.setDescription(factDto.getDescription());
                        result = factRepository.save(result);
                        factRepository.flush();
                    }
                } else {
                    // Create new entity
                    result = new Fact(optIndicator.get(),
                            factDto.getPointsSuggested(),
                            optUser.get(),
                            optUser.get(), // TODO: save logged in user as a creator
                            factDto.getDescription());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: Add normal logging!
        }
        return result;
    }

    @Transactional
    @Modifying
    public void delete(UUID id) {
        Optional optFact = factRepository.findById(id);
        if (optFact.isPresent()) {
            factRepository.delete((Fact) optFact.get());
        }
    }
}
