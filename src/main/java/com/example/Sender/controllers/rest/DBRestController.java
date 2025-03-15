package com.example.Sender.controllers.rest;

import com.example.Sender.dto.NewPersonDTO;
import com.example.Sender.dto.PersonDTO;
import com.example.Sender.services.DBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = DBRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DBRestController {

    static final String REST_URL = "/db/rest";

    private final DBService dbService;


    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PersonDTO>> getAllUsers() {
        var users = dbService.getAllClientsAndEmployees();
        return ResponseEntity.ok(users);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NewPersonDTO> saveForm(@RequestBody NewPersonDTO newPersonDTO) {
        dbService.savePerson(newPersonDTO);
        return ResponseEntity.ok(newPersonDTO);
    }

    @PostMapping(value = "/upload/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadCSVFile(@RequestParam("csvFile") MultipartFile csvFile) {
        if (csvFile.isEmpty()) {
            return ResponseEntity.badRequest().body("Файл не был передан");
        }
        try {
            dbService.parseCSV(csvFile);
            return ResponseEntity.ok("Файл успешно загружен");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка при загрузке файла");
        }
    }

    @DeleteMapping(value = "/delete/{email}/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePerson(@PathVariable String email, @PathVariable String type) {
        dbService.deletePerson(email, type);
        return ResponseEntity.ofNullable("Person is not found");
    }


}