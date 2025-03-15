package com.example.Sender.services;

import com.example.Sender.models.Employee;
import com.example.Sender.models.EmployeeType;
import com.example.Sender.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository employeeRepository;


    public Employee getEmployeeByEmail(String email){
        return employeeRepository.getEmployeeByEmail(email);
    }

    public List<Employee> getAllEmployeeByEmployeeType(EmployeeType employeeType){
        return employeeRepository.getEmployeesByEmployeeType(employeeType);
    }
    public List<Employee> getAllEmployee(){
       return  (List<Employee>) employeeRepository.findAll();
    }

    public void deleteEmployee(Employee employee){
        employeeRepository.delete(employee);
    }

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }



}
