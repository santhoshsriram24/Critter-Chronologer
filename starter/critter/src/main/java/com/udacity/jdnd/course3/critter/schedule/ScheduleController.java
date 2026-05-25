package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private com.udacity.jdnd.course3.critter.user.EmployeeRepository employeeRepository;

    @Autowired
    private com.udacity.jdnd.course3.critter.pet.PetRepository petRepository;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule sched = new Schedule();
        sched.setDate(scheduleDTO.getDate());
        sched.setActivities(scheduleDTO.getActivities());
        if (scheduleDTO.getEmployeeIds() != null) {
            sched.setEmployees(scheduleDTO.getEmployeeIds().stream().map(id -> employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"))).toList());
        }
        if (scheduleDTO.getPetIds() != null) {
            sched.setPets(scheduleDTO.getPetIds().stream().map(id -> petRepository.findById(id).orElseThrow(() -> new RuntimeException("Pet not found"))).toList());
        }
        Schedule saved = scheduleService.saveSchedule(sched);
        ScheduleDTO out = new ScheduleDTO();
        out.setId(saved.getId());
        out.setDate(saved.getDate());
        out.setActivities(saved.getActivities());
        out.setEmployeeIds(saved.getEmployees().stream().map(e -> e.getId()).toList());
        out.setPetIds(saved.getPets().stream().map(p -> p.getId()).toList());
        return out;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.getAllSchedules().stream().map(s -> {
            ScheduleDTO out = new ScheduleDTO();
            out.setId(s.getId());
            out.setDate(s.getDate());
            out.setActivities(s.getActivities());
            out.setEmployeeIds(s.getEmployees().stream().map(e -> e.getId()).toList());
            out.setPetIds(s.getPets().stream().map(p -> p.getId()).toList());
            return out;
        }).toList();
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.getAllSchedules().stream().filter(s -> s.getPets().stream().anyMatch(p -> p.getId().equals(petId))).map(s -> {
            ScheduleDTO out = new ScheduleDTO();
            out.setId(s.getId());
            out.setDate(s.getDate());
            out.setActivities(s.getActivities());
            out.setEmployeeIds(s.getEmployees().stream().map(e -> e.getId()).toList());
            out.setPetIds(s.getPets().stream().map(p -> p.getId()).toList());
            return out;
        }).toList();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.getAllSchedules().stream().filter(s -> s.getEmployees().stream().anyMatch(e -> e.getId().equals(employeeId))).map(s -> {
            ScheduleDTO out = new ScheduleDTO();
            out.setId(s.getId());
            out.setDate(s.getDate());
            out.setActivities(s.getActivities());
            out.setEmployeeIds(s.getEmployees().stream().map(e -> e.getId()).toList());
            out.setPetIds(s.getPets().stream().map(p -> p.getId()).toList());
            return out;
        }).toList();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.getAllSchedules().stream().filter(s -> s.getPets().stream().anyMatch(p -> p.getOwner().getId().equals(customerId))).map(s -> {
            ScheduleDTO out = new ScheduleDTO();
            out.setId(s.getId());
            out.setDate(s.getDate());
            out.setActivities(s.getActivities());
            out.setEmployeeIds(s.getEmployees().stream().map(e -> e.getId()).toList());
            out.setPetIds(s.getPets().stream().map(p -> p.getId()).toList());
            return out;
        }).toList();
    }
}
