package com.example.Sender.services.mail;


import com.example.Sender.dto.MailParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Override
    public String send(MailParams mailParams) {
        try {
            var subject = mailParams.getTopic();
            var messageBody = mailParams.getMessage();
            var emailTo = mailParams.getEmailTo();

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailFrom);
            mailMessage.setTo(emailTo);
            mailMessage.setSubject(subject);
            mailMessage.setText(messageBody);

            javaMailSender.send(mailMessage);
            return "Успешно отправлено";
        } catch (Exception e) {
            return "Ошибка при отправке";
        }

    }


}
