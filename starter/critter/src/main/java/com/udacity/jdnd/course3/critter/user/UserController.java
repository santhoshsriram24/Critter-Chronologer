package com.udacity.jdnd.course3.critter.user;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private com.udacity.jdnd.course3.critter.pet.PetRepository petRepository;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        com.udacity.jdnd.course3.critter.user.Customer c = new com.udacity.jdnd.course3.critter.user.Customer();
        c.setName(customerDTO.getName());
        c.setPhoneNumber(customerDTO.getPhoneNumber());
        c.setNotes(customerDTO.getNotes());
        com.udacity.jdnd.course3.critter.user.Customer saved = userService.saveCustomer(c);
        CustomerDTO out = new CustomerDTO();
        out.setId(saved.getId());
        out.setName(saved.getName());
        out.setPhoneNumber(saved.getPhoneNumber());
        out.setNotes(saved.getNotes());
        if (saved.getPets() != null) {
            out.setPetIds(saved.getPets().stream().map(p -> p.getId()).toList());
        }
        return out;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return userService.getAllCustomers().stream().map(c -> {
            CustomerDTO out = new CustomerDTO();
            out.setId(c.getId());
            out.setName(c.getName());
            out.setPhoneNumber(c.getPhoneNumber());
            out.setNotes(c.getNotes());
            if (c.getPets() != null) out.setPetIds(c.getPets().stream().map(p -> p.getId()).toList());
            return out;
        }).toList();
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        com.udacity.jdnd.course3.critter.pet.Pet pet = petRepository.findById(petId).orElseThrow(() -> new RuntimeException("Pet not found"));
        com.udacity.jdnd.course3.critter.user.Customer owner = pet.getOwner();
        CustomerDTO out = new CustomerDTO();
        out.setId(owner.getId());
        out.setName(owner.getName());
        out.setPhoneNumber(owner.getPhoneNumber());
        out.setNotes(owner.getNotes());
        if (owner.getPets() != null) out.setPetIds(owner.getPets().stream().map(p -> p.getId()).toList());
        return out;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        com.udacity.jdnd.course3.critter.user.Employee e = new com.udacity.jdnd.course3.critter.user.Employee();
        e.setName(employeeDTO.getName());
        e.setSkills(employeeDTO.getSkills());
        e.setDaysAvailable(employeeDTO.getDaysAvailable());
        com.udacity.jdnd.course3.critter.user.Employee saved = userService.saveEmployee(e);
        EmployeeDTO out = new EmployeeDTO();
        out.setId(saved.getId());
        out.setName(saved.getName());
        out.setSkills(saved.getSkills());
        out.setDaysAvailable(saved.getDaysAvailable());
        return out;
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        com.udacity.jdnd.course3.critter.user.Employee emp = userService.findEmployeeById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        EmployeeDTO out = new EmployeeDTO();
        out.setId(emp.getId());
        out.setName(emp.getName());
        out.setSkills(emp.getSkills());
        out.setDaysAvailable(emp.getDaysAvailable());
        return out;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        com.udacity.jdnd.course3.critter.user.Employee emp = userService.findEmployeeById(employeeId).orElseThrow(() -> new RuntimeException("Employee not found"));
        emp.setDaysAvailable(daysAvailable);
        userService.saveEmployee(emp);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<com.udacity.jdnd.course3.critter.user.Employee> all = userService.getAllEmployees();
        return all.stream().filter(e -> {
            return e.getDaysAvailable() != null && e.getDaysAvailable().contains(employeeDTO.getDate().getDayOfWeek())
                    && e.getSkills() != null && e.getSkills().containsAll(employeeDTO.getSkills());
        }).map(emp -> {
            EmployeeDTO out = new EmployeeDTO();
            out.setId(emp.getId());
            out.setName(emp.getName());
            out.setDaysAvailable(emp.getDaysAvailable());
            out.setSkills(emp.getSkills());
            return out;
        }).toList();
    }

}
