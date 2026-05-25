package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeRepository;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule) { return scheduleRepository.save(schedule); }

    public List<Schedule> getAllSchedules() { return scheduleRepository.findAll(); }

    public List<Schedule> findByPetId(Long petId) { return scheduleRepository.findAllByPets_IdOrderByDate(petId); }

    public List<Schedule> findByEmployeeId(Long employeeId) { return scheduleRepository.findAllByEmployees_IdOrderByDate(employeeId); }

    public List<Schedule> findByCustomerId(Long ownerId) { return scheduleRepository.findAllByPetsOwner_IdOrderByDate(ownerId); }
    
}
