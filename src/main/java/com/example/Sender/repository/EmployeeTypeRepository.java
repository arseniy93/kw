package com.example.Sender.repository;

import com.example.Sender.models.EmployeeType;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeTypeRepository extends CrudRepository<EmployeeType, Integer> {
    EmployeeType getEmployeeTypeByName(String name);
}
