package com.example.Sender.repository;

import com.example.Sender.models.Employee;
import com.example.Sender.models.EmployeeType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    List<Employee> getEmployeesByEmployeeType(EmployeeType employeeType);

    Employee getEmployeeByEmail(String email);
}
