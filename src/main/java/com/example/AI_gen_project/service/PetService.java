package com.example.AI_gen_project.service;

import com.example.AI_gen_project.entity.Pet;
import com.example.AI_gen_project.dto.PetSummary;
import com.example.AI_gen_project.dto.PetStatistics;
import java.util.List;

public interface PetService {
    Pet createPet(Pet pet);
    List<Pet> getAllPets();
    Pet getPetById(Long id);
    Pet updatePet(Long id, Pet pet);
    void deletePetById(Long id);
    void deletePetsByName(String name);
    List<Pet> getPetsByAnimalType(String animalType);
    List<Pet> getPetsByBreed(String breed);
    List<PetSummary> getPetSummaries();
    PetStatistics getPetStatistics();
}