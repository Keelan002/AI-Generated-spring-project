package com.example.AI_gen_project.controller;

import com.example.AI_gen_project.dto.*;
import com.example.AI_gen_project.entity.*;
import com.example.AI_gen_project.service.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;
    private final HouseholdService householdService;

    public PetController(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPet(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public Pet createPet(@Valid @RequestBody CreatePetRequest request) {
        Household household = householdService.getHouseholdByEircode(request.householdEircode());

        Pet pet = Pet.builder()
                .name(request.name())
                .animalType(request.animalType())
                .breed(request.breed())
                .age(request.age())
                .household(household)
                .build();

        return petService.createPet(pet);
    }

    @PatchMapping("/{id}/name")
    public Pet updatePetName(@PathVariable Long id, @Valid @RequestBody UpdatePetNameRequest request) {
        Pet pet = petService.getPetById(id);
        pet.setName(request.name());
        return petService.updatePet(id, pet);
    }
}
