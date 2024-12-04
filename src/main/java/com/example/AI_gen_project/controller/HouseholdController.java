package com.example.AI_gen_project.controller;

import com.example.AI_gen_project.dto.CreateHouseholdRequest;
import com.example.AI_gen_project.entity.Household;
import com.example.AI_gen_project.service.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {
    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @GetMapping("/no-pets")
    public List<Household> getHouseholdsWithNoPets() {
        return householdService.findHouseholdsWithNoPets();
    }

    @GetMapping("/{eircode}")
    public Household getHousehold(@PathVariable String eircode) {
        return householdService.getHouseholdByEircode(eircode);
    }

    @DeleteMapping("/{eircode}")
    public ResponseEntity<Void> deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHouseholdByEircode(eircode);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public Household createHousehold(@Valid @RequestBody CreateHouseholdRequest request) {
        Household household = Household.builder()
                .eircode(request.eircode())
                .numberOfOccupants(request.numberOfOccupants())
                .maxNumberOfOccupants(request.maxNumberOfOccupants())
                .ownerOccupied(request.ownerOccupied())
                .build();

        return householdService.createHousehold(household);
    }
}