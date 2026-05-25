package com.udacity.jdnd.course3.critter.pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setName(petDTO.getName());
        pet.setType(petDTO.getType());
        pet.setBirthDate(petDTO.getBirthDate());
        pet.setNotes(petDTO.getNotes());
        if (petDTO.getOwnerId() > 0) {
            com.udacity.jdnd.course3.critter.user.Customer owner = new com.udacity.jdnd.course3.critter.user.Customer();
            owner.setId(petDTO.getOwnerId());
            pet.setOwner(owner);
        }
        Pet saved = petService.savePet(pet);
        PetDTO out = new PetDTO();
        out.setId(saved.getId());
        out.setName(saved.getName());
        out.setType(saved.getType());
        out.setBirthDate(saved.getBirthDate());
        out.setNotes(saved.getNotes());
        out.setOwnerId(saved.getOwner() != null ? saved.getOwner().getId() : 0);
        return out;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        Pet pet = petService.getPet(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        PetDTO out = new PetDTO();
        out.setId(pet.getId());
        out.setName(pet.getName());
        out.setType(pet.getType());
        out.setBirthDate(pet.getBirthDate());
        out.setNotes(pet.getNotes());
        out.setOwnerId(pet.getOwner() != null ? pet.getOwner().getId() : 0);
        return out;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return petService.getAllPets().stream().map(p -> {
            PetDTO out = new PetDTO();
            out.setId(p.getId());
            out.setName(p.getName());
            out.setType(p.getType());
            out.setBirthDate(p.getBirthDate());
            out.setNotes(p.getNotes());
            out.setOwnerId(p.getOwner() != null ? p.getOwner().getId() : 0);
            return out;
        }).toList();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return petService.getPetsByOwner(ownerId).stream().map(p -> {
            PetDTO out = new PetDTO();
            out.setId(p.getId());
            out.setName(p.getName());
            out.setType(p.getType());
            out.setBirthDate(p.getBirthDate());
            out.setNotes(p.getNotes());
            out.setOwnerId(p.getOwner() != null ? p.getOwner().getId() : 0);
            return out;
        }).toList();
    }
}
