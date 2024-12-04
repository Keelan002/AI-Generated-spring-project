package com.example.AI_gen_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.AI_gen_project.entity.Pet;
import com.example.AI_gen_project.dto.PetSummary;
import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    // Delete pets by name (ignore case)
    void deleteByNameIgnoreCase(String name);

    // Find pets by animal type
    List<Pet> findByAnimalTypeIgnoreCase(String animalType);

    // Find pets by breed ordered by age
    List<Pet> findByBreedIgnoreCaseOrderByAgeAsc(String breed);

    // Get name and breed only using projection
    @Query("SELECT new com.example.demo.dto.PetSummary(p.name, p.animalType, p.breed) FROM Pet p")
    List<PetSummary> findPetSummaries();

    // Get pet statistics
    @Query("SELECT AVG(p.age) FROM Pet p")
    Double findAverageAge();

    @Query("SELECT MAX(p.age) FROM Pet p")
    Integer findOldestAge();

    @Query("SELECT COUNT(p) FROM Pet p")
    Long getTotalCount();
}