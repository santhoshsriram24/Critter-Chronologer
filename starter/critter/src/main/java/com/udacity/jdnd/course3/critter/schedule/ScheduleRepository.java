package com.udacity.jdnd.course3.critter.schedule;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByPets_IdOrderByDate(Long petId);
    List<Schedule> findAllByEmployees_IdOrderByDate(Long employeeId);
    List<Schedule> findAllByPetsOwner_IdOrderByDate(Long ownerId);
}
