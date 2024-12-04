package com.example.AI_gen_project.service;

import com.example.AI_gen_project.entity.Pet;
import com.example.AI_gen_project.repository.PetRepository;
import com.example.AI_gen_project.exception.PetNotFoundException;
import com.example.AI_gen_project.exception.InvalidPetDataException;
import com.example.AI_gen_project.dto.PetSummary;
import com.example.AI_gen_project.dto.PetStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        validatePet(pet);
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("Pet not found with id: " + id));
    }

    @Override
    public Pet updatePet(Long id, Pet pet) {
        validatePet(pet);
        Pet existingPet = getPetById(id);

        existingPet.setName(pet.getName());
        existingPet.setAnimalType(pet.getAnimalType());
        existingPet.setBreed(pet.getBreed());
        existingPet.setAge(pet.getAge());

        return petRepository.save(existingPet);
    }

    @Override
    @Transactional
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new PetNotFoundException("Pet not found with id: " + id);
        }
        petRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deletePetsByName(String name) {
        petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Pet> getPetsByAnimalType(String animalType) {
        if (animalType == null || animalType.trim().isEmpty()) {
            throw new InvalidPetDataException("Animal type cannot be empty");
        }
        return petRepository.findByAnimalTypeIgnoreCase(animalType);
    }

    @Override
    public List<Pet> getPetsByBreed(String breed) {
        if (breed == null || breed.trim().isEmpty()) {
            throw new InvalidPetDataException("Breed cannot be empty");
        }
        return petRepository.findByBreedIgnoreCaseOrderByAgeAsc(breed);
    }

    @Override
    public List<PetSummary> getPetSummaries() {
        return petRepository.findPetSummaries();
    }

    @Override
    public PetStatistics getPetStatistics() {
        return new PetStatistics(
                petRepository.findAverageAge(),
                petRepository.findOldestAge(),
                petRepository.getTotalCount()
        );
    }

    private void validatePet(Pet pet) {
        if (pet == null) {
            throw new InvalidPetDataException("Pet cannot be null");
        }
        if (pet.getName() == null || pet.getName().trim().isEmpty()) {
            throw new InvalidPetDataException("Pet name cannot be empty");
        }
        if (pet.getAnimalType() == null || pet.getAnimalType().trim().isEmpty()) {
            throw new InvalidPetDataException("Animal type cannot be empty");
        }
        if (pet.getBreed() == null || pet.getBreed().trim().isEmpty()) {
            throw new InvalidPetDataException("Breed cannot be empty");
        }
        if (pet.getAge() == null || pet.getAge() < 0) {
            throw new InvalidPetDataException("Age must be a non-negative number");
        }
    }
}