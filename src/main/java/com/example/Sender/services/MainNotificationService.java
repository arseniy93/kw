package com.example.Sender.services;

import com.example.Sender.dto.MailParams;
import com.example.Sender.dto.NotificationDTO;
import com.example.Sender.models.*;
import com.example.Sender.repository.*;
import com.example.Sender.services.mail.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


@Service
public class MainNotificationService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientTypeRepository clientTypeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;
    @Autowired
    private LetterRepository letterRepository;
    @Autowired
    private NewsletterRepository newsletterRepository;

    private final MailSenderService mailSenderService;

    public MainNotificationService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }


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
                List<Employee> employees = (List<Employee>) employeeRepository.findAll();
                mailAddresses = employees.stream().map(Employee::getEmail).toList();
            } else {
                //TODO check notification set aop
                EmployeeType employeeType = employeeTypeRepository.getEmployeeTypeById(Integer.parseInt(notificationDTO.getClientType()));
                mailAddresses = employeeRepository.getEmployeesByEmployeeType(employeeType)
                        .stream().map(Employee::getEmail).toList();
            }
        } else {
            newsletter.setDestination("Клиенты");
            if (type.equals("все")) {
                List<Client> employees = (List<Client>) clientRepository.findAll();
                mailAddresses = employees.stream().map(Client::getEmail).toList();
            } else {
                ClientType clientType = clientTypeRepository.getClientTypeById(Integer.parseInt(notificationDTO.getClientType()));
                mailAddresses = clientRepository.getAllByClientType(clientType)
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
        newsletterRepository.save(newsletter);

        for (var letter : letters) {
            letter.setNewsletter(newsletterRepository.getByDateTime(dateTime));
            letterRepository.save(letter);
        }
    }


}
