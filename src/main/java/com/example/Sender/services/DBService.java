package com.example.Sender.services;

import com.example.Sender.dto.NewPersonDTO;
import com.example.Sender.dto.PersonDTO;
import com.example.Sender.models.Client;
import com.example.Sender.models.Employee;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.Sender.constants.Constants.CLIENT;
import static com.example.Sender.constants.Constants.EMPLOYEE;

@Service
@RequiredArgsConstructor
public class DBService {

    //TODO set service for all repository
    private final ClientService clientService;
    private final ClientTypeService clientTypeService;
    private final EmployeeService employeeService;
    private final EmployeeTypeService employeeTypeService;

    public List<PersonDTO> getAllClientsAndEmployees() {
        List<Client> clients = clientService.getClients();
        List<Employee> employees = employeeService.getAllEmployee();

        return Stream.concat(
                clients.stream().map(client -> new PersonDTO(
                        client.getId(),
                        client.getFirstname(),
                        client.getLastname(),
                        client.getMiddleName(),
                        CLIENT+": " + client.getClientType().getName(),
                        client.getEmail()
                )),
                employees.stream().map(employee -> new PersonDTO(
                        employee.getId(),
                        employee.getFirstname(),
                        employee.getLastname(),
                        employee.getMiddleName(),
                        EMPLOYEE+": " + employee.getEmployeeType().getName(),
                        employee.getEmail()
                ))
        ).collect(Collectors.toList());
    }
//TODO getUserAge
    public void savePerson(NewPersonDTO personDTO) {
        if (personDTO.getType().equals("employee") || personDTO.getType().equals(EMPLOYEE)) {
            Employee person = new Employee();
            person.setEmail(personDTO.getMail());
            person.setFirstname(personDTO.getFirstname());
            person.setLastname(personDTO.getLastname());
            person.setMiddleName(personDTO.getMiddlename());
            person.setEmployeeType(employeeTypeService.getEmployeeTypeByName("новый"));//TODO на стороне фронта пустой фрагамент - Давность пользователя , нужно вставить personDTO.getUserAge() после исправления
            employeeService.saveEmployee(person);
        } else {
            Client person = new Client();
            person.setEmail(personDTO.getMail());
            person.setFirstname(personDTO.getFirstname());
            person.setLastname(personDTO.getLastname());
            person.setMiddleName(personDTO.getMiddlename());
            person.setClientType(clientTypeService.getClientTypeByName(personDTO.getUserAge()));
            clientService.saveClient(person);
        }
    }

    public void deletePerson(String email, String type) {
        if (type.toLowerCase().startsWith(CLIENT)) {
            Client client = clientService.getClientByEmail(email);
            clientService.deleteClient(client);
        } else {
            Employee employee = employeeService.getEmployeeByEmail(email);
            employeeService.deleteEmployee(employee);
        }
    }

    public void parseCSV(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReader(reader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                // Парсинг каждой строки CSV файла
                if (nextRecord.length == 6) { // Проверяем, что количество столбцов верное
                    NewPersonDTO person = new NewPersonDTO();
                    person.setLastname(nextRecord[0].trim());
                    person.setFirstname(nextRecord[1].trim());
                    person.setMiddlename(nextRecord[2].trim());
                    person.setType(nextRecord[3].trim());
                    person.setUserAge(nextRecord[4].trim());
                    person.setMail(nextRecord[5].trim());
                    savePerson(person);
                } else {
                    throw new IllegalArgumentException("Неверный формат CSV файла");
                }

            }
        } catch (CsvValidationException | IOException e) {
            throw new RuntimeException(e);
        }

    }
}
