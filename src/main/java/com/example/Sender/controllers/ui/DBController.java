package com.example.Sender.controllers.ui;

import com.example.Sender.dto.NewPersonDTO;
import com.example.Sender.dto.PersonDTO;
import com.example.Sender.services.DBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/db")
public class DBController {
    DBService dbService;

    public DBController(DBService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        List<PersonDTO> persons = dbService.getAllClientsAndEmployees();
        model.addAttribute("persons", persons);
        return "db";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/db/main";
    }


    @PostMapping(
            value = "/save",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String saveForm(@RequestBody NewPersonDTO newPersonDTO) {
        dbService.savePerson(newPersonDTO);
        return "redirect:/db/main";
    }

    @PostMapping("/upload/csv")
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

    @DeleteMapping("/delete/{email}/{type}")
    public String delete(@PathVariable String email, @PathVariable String type) {
        dbService.deletePerson(email, type);
        return "redirect:/db/main";
    }

}