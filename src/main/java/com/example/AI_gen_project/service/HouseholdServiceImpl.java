package com.example.AI_gen_project.service;

import com.example.AI_gen_project.entity.Household;
import com.example.AI_gen_project.repository.HouseholdRepository;
import com.example.AI_gen_project.exception.HouseholdNotFoundException;
import com.example.AI_gen_project.exception.InvalidHouseholdDataException;
import com.example.AI_gen_project.dto.HouseholdStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;

    @Autowired
    public HouseholdServiceImpl(HouseholdRepository householdRepository) {
        this.householdRepository = householdRepository;
    }

    @Override
    public Household createHousehold(Household household) {
        validateHousehold(household);
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household getHouseholdByEircode(String eircode) {
        return householdRepository.findById(eircode)
                .orElseThrow(() -> new HouseholdNotFoundException("Household not found with eircode: " + eircode));
    }

    @Override
    public Household getHouseholdByEircodeWithPets(String eircode) {
        Household household = householdRepository.findByEircodeWithPets(eircode);
        if (household == null) {
            throw new HouseholdNotFoundException("Household not found with eircode: " + eircode);
        }
        return household;
    }

    @Override
    @Transactional
    public Household updateHousehold(String eircode, Household household) {
        validateHousehold(household);
        Household existingHousehold = getHouseholdByEircode(eircode);

        existingHousehold.setNumberOfOccupants(household.getNumberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(household.getMaxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(household.getOwnerOccupied());

        return householdRepository.save(existingHousehold);
    }

    @Override
    @Transactional
    public void deleteHouseholdByEircode(String eircode) {
        if (!householdRepository.existsById(eircode)) {
            throw new HouseholdNotFoundException("Household not found with eircode: " + eircode);
        }
        householdRepository.deleteById(eircode);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findByOwnerOccupiedTrue();
    }

    @Override
    public HouseholdStatistics getHouseholdStatistics() {
        return new HouseholdStatistics(
                householdRepository.countEmptyHouses(),
                householdRepository.countFullHouses()
        );
    }

    private void validateHousehold(Household household) {
        if (household == null) {
            throw new InvalidHouseholdDataException("Household cannot be null");
        }
        if (household.getEircode() == null || household.getEircode().trim().isEmpty()) {
            throw new InvalidHouseholdDataException("Eircode cannot be empty");
        }
        if (household.getNumberOfOccupants() == null || household.getNumberOfOccupants() < 0) {
            throw new InvalidHouseholdDataException("Number of occupants must be non-negative");
        }
        if (household.getMaxNumberOfOccupants() == null || household.getMaxNumberOfOccupants() <= 0) {
            throw new InvalidHouseholdDataException("Maximum number of occupants must be positive");
        }
        if (household.getNumberOfOccupants() > household.getMaxNumberOfOccupants()) {
            throw new InvalidHouseholdDataException("Number of occupants cannot exceed maximum capacity");
        }
        if (household.getOwnerOccupied() == null) {
            throw new InvalidHouseholdDataException("Owner occupied status must be specified");
        }
    }
}
