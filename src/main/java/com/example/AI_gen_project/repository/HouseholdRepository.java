package com.example.AI_gen_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.AI_gen_project.entity.Household;
import java.util.List;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, String> {

    @Query("SELECT h FROM Household h LEFT JOIN FETCH h.pets")
    List<Household> findAllWithPets();

    @Query("SELECT h FROM Household h LEFT JOIN FETCH h.pets WHERE h.eircode = ?1")
    Household findByEircodeWithPets(String eircode);

    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> findHouseholdsWithNoPets();

    List<Household> findByOwnerOccupiedTrue();

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = 0")
    Long countEmptyHouses();

    @Query("SELECT COUNT(h) FROM Household h WHERE h.numberOfOccupants = h.maxNumberOfOccupants")
    Long countFullHouses();
}
