package com.example.AI_gen_project.controller.graphql;

record HouseholdInput(
        String eircode,
        Integer numberOfOccupants,
        Integer maxNumberOfOccupants,
        Boolean ownerOccupied
) {}
