package com.example.Sender.services;

import com.example.Sender.dto.MailParams;
import com.example.Sender.dto.NotificationDTO;
import com.example.Sender.models.*;
import com.example.Sender.repository.*;
import com.example.Sender.services.mail.MailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Service
public class MainNotificationService {
    private final ClientTypeService clientTypeService;
    private final ClientService clientService;
    private final EmployeeService employeeService;
    private final EmployeeTypeService employeeTypeService;
    private final LetterRepository letterRepository;
    private final NewsLetterService letterService;
    private final MailSenderService mailSenderService;



    public void send(NotificationDTO notificationDTO, boolean flag) {
        String type = notificationDTO.getClientType();
        String topic = notificationDTO.getTopicText();
        String message = notificationDTO.getMessageText();
        LocalDateTime dateTime = LocalDateTime.now();

        Newsletter newsletter = new Newsletter();
        newsletter.setDateTime(dateTime);
        newsletter.setText(message);
        newsletter.setTopic(topic);

        List<String> mailAddresses;
        if (flag) {
            newsletter.setDestination("Сотрудники");
            if (type.equals("все")) {
                List<Employee> employees = employeeService.getAllEmployee();
                mailAddresses = employees.stream().map(Employee::getEmail).toList();
            } else {
                //TODO check notification set aop
                EmployeeType employeeType = employeeTypeService.getEmployeeTypeByName(notificationDTO.getClientType());
                mailAddresses = employeeService.getAllEmployeeByEmployeeType(employeeType)
                        .stream().map(Employee::getEmail).toList();
            }
        } else {
            newsletter.setDestination("Клиенты");
            if (type.equals("все")) {
                List<Client> employees = clientService.getClients();
                mailAddresses = employees.stream().map(Client::getEmail).toList();
            } else {
                ClientType clientType = clientTypeService.getClientTypeByName(notificationDTO.getClientType());
                mailAddresses = clientService.getAllClientByClientType(clientType)
                        .stream().map(Client::getEmail).toList();
            }
        }

        newsletter.setNumberOfSent(mailAddresses.size());
        List<Letter> letters = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Letter>> futures = new ArrayList<>();

        for (var mail : mailAddresses) {
            Future<Letter> future = executorService.submit(() -> {
                MailParams params = new MailParams();
                params.setTopic(topic);
                params.setMessage(message);
                params.setEmailTo(mail);

                String status = mailSenderService.send(params);
                Letter letter = new Letter();
                letter.setLetterType(type);
                letter.setSentStatus(status);
                letter.setDestinationAddress(mail);

                return letter;
            });
            futures.add(future);
        }

        executorService.shutdown();

        String fullStatus = "Успешно доставлено";
        try {
            for (Future<Letter> future : futures) {
                Letter letter = future.get();
                if (letter.getSentStatus().equals("Ошибка при отправке")) {
                    fullStatus = "Ошибка при отправке";
                }
                letters.add(letter);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        newsletter.setStatus(fullStatus);
        letterService.save(newsletter);

        for (var letter : letters) {
            letter.setNewsletter(letterService.getByDateTime(dateTime));
            letterRepository.save(letter);
        }
    }


}
