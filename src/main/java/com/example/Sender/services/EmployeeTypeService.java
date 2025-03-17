package com.example.Sender.services;


import com.example.Sender.models.EmployeeType;
import com.example.Sender.repository.EmployeeTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeTypeService {

    private final EmployeeTypeRepository employeeTypeRepository;



    public EmployeeType getEmployeeTypeByName(String name){
        return employeeTypeRepository.getEmployeeTypeByName(name);
    }

}
