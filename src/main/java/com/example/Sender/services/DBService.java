package com.example.Sender.services;

import com.example.Sender.dto.NewPersonDTO;
import com.example.Sender.dto.PersonDTO;
import com.example.Sender.models.Client;
import com.example.Sender.models.Employee;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class DBService {
    //TODO set service for all repository
    private final ClientService clientService;
    private final EntityTypeService entityTypeService;
    private final EmployeeService employeeService;

    public List<PersonDTO> getAllClientsAndEmployees() {
        List<Client> clients = clientService.getClients();
        List<Employee> employees = employeeService.getAllEmployee();

        return Stream.concat(
                clients.stream().map(client -> new PersonDTO(
                        client.getId(),
                        client.getFirstname(),
                        client.getLastname(),
                        client.getMiddleName(),
                        "Клиент: " + client.getClientEntityType().getParameter(),
                        client.getEmail()
                )),
                employees.stream().map(employee -> new PersonDTO(
                        employee.getId(),
                        employee.getFirstname(),
                        employee.getLastname(),
                        employee.getMiddleName(),
                        "Сотрудник: " + employee.getEmployeeEntityType().getParameter(),
                        employee.getEmail()
                ))
        ).collect(Collectors.toList());
    }

    public void savePerson(NewPersonDTO personDTO) {
        var type = entityTypeService.getEntityTypeByParameter(personDTO.getType());
        if (personDTO.getEntity().equals("employee") || personDTO.getType().equals("Сотрудник")) {
            Employee person = new Employee();
            person.setEmail(personDTO.getMail());
            person.setFirstname(personDTO.getFirstname());
            person.setLastname(personDTO.getLastname());
            person.setMiddleName(personDTO.getMiddlename());
            person.setEmployeeEntityType(type);//getUserAge()?
            employeeService.saveEmployee(person);
        } else {
            Client person = new Client();
            person.setEmail(personDTO.getMail());
            person.setFirstname(personDTO.getFirstname());
            person.setLastname(personDTO.getLastname());
            person.setMiddleName(personDTO.getMiddlename());
            person.setClientEntityType(type);
            clientService.saveClient(person);
        }
    }

    public boolean deletePerson(String email, String type) {
        var isDeletedPerson = false;
        if (type.startsWith("Клиент")) {
            var client = clientService.findClientByEmail(email);
            if (client.isPresent()) {
                clientService.deleteClient(client.get());
                return true;

            } else {
                return isDeletedPerson;
            }

        } else {
            var employee = employeeService.findEmployeeByEmail(email);
            if (employee.isPresent()) {
                employeeService.deleteEmployee(employee.get());
                return true;

            } else {
                return isDeletedPerson;
            }
        }
    }


    public void parseCSV(MultipartFile file) {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {  // Укажите кодировку UTF-8
            CSVReader csvReader = new CSVReader(reader);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                log.info("Строка CSV: " + Arrays.toString(nextRecord));
                if (nextRecord.length == 1) {
                    NewPersonDTO person = new NewPersonDTO();
                    person.setLastname(nextRecord[0].trim());
                    person.setFirstname(nextRecord[1].trim());
                    person.setMiddlename(nextRecord[2].trim());
                    try {
                        String typeParameter = entityTypeService.getEntityTypeByParameter(nextRecord[3].trim()).getParameter();
                        person.setType(typeParameter);
                    } catch (IllegalArgumentException e) {
                        log.error("Недопустимый тип сущности: " + nextRecord[3].trim());
                        throw new IllegalArgumentException("Недопустимый тип сущности: " + nextRecord[3].trim(), e);
                    }
                    person.setEntity(nextRecord[4].trim());
                    person.setMail(nextRecord[5].trim());
                    savePerson(person);
                } else {
                    log.error("Неверное количество столбцов в строке CSV: " + Arrays.toString(nextRecord));
                    throw new IllegalArgumentException("Неверный формат CSV файла. Ожидалось 6 столбцов, получено: " + nextRecord.length);
                }
            }
        } catch (CsvValidationException | IOException e) {
            log.error("Ошибка при чтении CSV: " + e.getMessage());
            throw new RuntimeException("Ошибка при чтении CSV файла", e);
        }
    }


//    public void parseCSV(MultipartFile file) {
//        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
//            CSVReader csvReader = new CSVReader(reader);
//            var nextRecord=new String[6];
//            while ((nextRecord = csvReader.readNext()) != null) {
//                // Парсинг каждой строки CSV файла
//                if (nextRecord.length == 6) { // Проверяем, что количество столбцов верное
//                    NewPersonDTO person = new NewPersonDTO();
//                    person.setLastname(nextRecord[0].trim());
//                    person.setFirstname(nextRecord[1].trim());
//                    person.setMiddlename(nextRecord[2].trim());
//                    person.setType(entityTypeService.getEntityTypeByParameter(nextRecord[3].trim()).getParameter());// проверка, чтобы тип соответствовал типам в EnumType
//                    person.setUserAge(nextRecord[4].trim());
//                    person.setMail(nextRecord[5].trim());
//                    savePerson(person);
//                } else {
//                    throw new IllegalArgumentException("Неверный формат CSV файла");
//                }
//
//            }
//        } catch (CsvValidationException | IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

}
