package com.example.Sender.services;

import com.example.Sender.enums.EntityType;
import com.example.Sender.models.Client;
import com.example.Sender.models.Employee;
import com.example.Sender.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;



    public List<Employee> getAllEmployee(){
       return  (List<Employee>) employeeRepository.findAll();
    }

    public void deleteEmployee(Employee employee){
        employeeRepository.delete(employee);
    }

    public Optional<Employee> findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public  List<Employee> getAllEmployeesByEmployeeEntityType(EntityType employeeType){
        return employeeRepository.getEmployeesByEmployeeEntityType(employeeType);
    }




}
