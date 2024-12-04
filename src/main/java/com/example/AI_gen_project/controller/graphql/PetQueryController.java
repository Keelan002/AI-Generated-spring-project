package com.example.AI_gen_project.controller.graphql;

import com.example.AI_gen_project.entity.*;
import com.example.AI_gen_project.service.*;
import com.example.AI_gen_project.dto.HouseholdStatistics;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class PetQueryController {
    private final PetService petService;
    private final HouseholdService householdService;

    public PetQueryController(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    @QueryMapping
    public Iterable<Household> households() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public Iterable<Pet> petsByAnimalType(@Argument String animalType) {
        return petService.getPetsByAnimalType(animalType);
    }

    @QueryMapping
    public Household household(@Argument String eircode) {
        return householdService.getHouseholdByEircode(eircode);
    }

    @QueryMapping
    public Pet pet(@Argument Long id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    public HouseholdStatistics statistics() {
        return householdService.getHouseholdStatistics();
    }

    @MutationMapping
    public Household createHousehold(@Argument HouseholdInput household) {
        return householdService.createHousehold(
                Household.builder()
                        .eircode(household.eircode())
                        .numberOfOccupants(household.numberOfOccupants())
                        .maxNumberOfOccupants(household.maxNumberOfOccupants())
                        .ownerOccupied(household.ownerOccupied())
                        .build()
        );
    }

    @MutationMapping
    public boolean deleteHousehold(@Argument String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return true;
    }

    @MutationMapping
    public boolean deletePet(@Argument Long id) {
        petService.deletePetById(id);
        return true;
    }
}

