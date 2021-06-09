package code.flatura.edukpi.controller;

import code.flatura.edukpi.model.Fact;
import code.flatura.edukpi.model.DTO.FactDto;
import code.flatura.edukpi.repository.FactRepository;
import code.flatura.edukpi.repository.IndicatorRepository;
import code.flatura.edukpi.repository.UserRepository;
import code.flatura.edukpi.service.FactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller(value = "/facts")
public class FactController {


    private FactRepository factRepository;

    private IndicatorRepository indicatorRepository;

    private UserRepository userRepository;

    private FactService factService;


    @Autowired
    public FactController(FactRepository factRepository, IndicatorRepository indicatorRepository, UserRepository userRepository, FactService factService) {
        this.factRepository = factRepository;
        this.indicatorRepository = indicatorRepository;
        this.userRepository = userRepository;
        this.factService = factService;
    }

    @GetMapping("/facts/add")
    public ModelAndView addFact(Map<String, Object> model) {
        FactDto factDto = new FactDto();
        model.put("fact", factDto);
        // TODO Add current logged user as default value of dto
        return new ModelAndView("fact_add_form", model);
    }

    @GetMapping("/facts")
    public ModelAndView getAllFacts(Map<String, Object> model) {
        List<Fact> facts = factRepository.findAll();
        List<FactDto> factsDto = FactDto.multipleConvertFrom(facts);
        model.put("facts_list", factsDto);
        return new ModelAndView("facts_all", model);
    }

    @GetMapping("/facts/{id}")
    public ModelAndView getFactById(Map<String, Object> model, @PathVariable(value="id") String idStr) {
        UUID id;
        try {
            id = UUID.fromString(idStr);
        } catch (Exception e) {
            e.printStackTrace();
            return new ModelAndView("facts", model);
        }
            Optional<Fact> opt = factRepository.findById(id);
            if (opt.isPresent()) {
                // Put fact entity into a model
                model.put("fact", FactDto.convertFrom(opt.get()));

                // Preparing indicators info - current indicator id and list of all indicators
                Map<String, UUID> indicators = new TreeMap<>();
                indicatorRepository.findAll().forEach(i -> indicators.put(i.getName(), i.getId()));
                model.put("indicators_list", indicators);

                // Preparing users info - current user id and list of all users
                Map<String, UUID> users = new TreeMap<>();
                userRepository.findAll().forEach(u -> users.put(u.getSurname() + " " + u.getName(), u.getId()));
                model.put("employees_list", users);

                return new ModelAndView("fact_edit_form", model);
            }
        return new ModelAndView("facts_form", model);
    }

    /**
     * If id is null or cannot be parsed than we have a new entity,
     * otherwise we have to try to update existing.
     */
    @PostMapping("/facts/{id}")
    public ModelAndView setFact(Map<String, Object> model,
                                @PathVariable(name = "id") String idStr,
                                @ModelAttribute(value="fact") FactDto factDto) {
        UUID id = null;
        try {
            id = UUID.fromString(idStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        factService.update(id, factDto);
        return new ModelAndView("redirect:/facts");
    }

    @DeleteMapping("/facts/delete/{id}")
    public ModelAndView deleteFact(Map<String, Object> model,
                                   @PathVariable(name = "id") String idStr) {
        factService.delete(UUID.fromString(idStr));
        return new ModelAndView("redirect:/facts");
    }
}
