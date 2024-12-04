package com.example.AI_gen_project.service;

import com.example.AI_gen_project.entity.Household;
import com.example.AI_gen_project.dto.HouseholdStatistics;
import java.util.List;

public interface HouseholdService {
    Household createHousehold(Household household);
    List<Household> getAllHouseholds();
    Household getHouseholdByEircode(String eircode);
    Household getHouseholdByEircodeWithPets(String eircode);
    Household updateHousehold(String eircode, Household household);
    void deleteHouseholdByEircode(String eircode);
    List<Household> findHouseholdsWithNoPets();
    List<Household> findOwnerOccupiedHouseholds();
    HouseholdStatistics getHouseholdStatistics();
}
