package com.udacity.jdnd.course3.critter.pet;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(Pet pet) {
        // ensure owner relationship is consistent
        if (pet.getOwner() != null && pet.getOwner().getId() != null) {
            Optional<Customer> customer = customerRepository.findById(pet.getOwner().getId());
            customer.ifPresent(c -> {
                pet.setOwner(c);
                c.getPets().add(pet);
            });
        }
        return petRepository.save(pet);
    }

    public java.util.Optional<Pet> getPet(Long id) { return petRepository.findById(id); }

    public List<Pet> getAllPets() { return petRepository.findAll(); }

    public List<Pet> getPetsByOwner(Long ownerId) { return petRepository.findByOwnerId(ownerId); }
}
