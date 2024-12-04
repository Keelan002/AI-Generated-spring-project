package com.example.AI_gen_project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "household")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Household {

    @Id
    private String eircode;

    private Integer numberOfOccupants;

    private Integer maxNumberOfOccupants;

    private Boolean ownerOccupied;

    @OneToMany(mappedBy = "household", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Pet> pets;
}