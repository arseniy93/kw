package com.example.Sender.repository;

import com.example.Sender.enums.EntityType;
import com.example.Sender.models.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> getEmployeesByEmployeeEntityType(EntityType employeeType);//clean cash


    Optional<Employee> findEmployeeByEmail(String email);
}
