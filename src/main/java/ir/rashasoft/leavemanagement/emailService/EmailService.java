package ir.rashasoft.leavemanagement.emailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void send(String to, String subject, String body) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    mimeMessageHelper.addTo(to);
                    mimeMessageHelper.setSubject(subject);
                    mimeMessageHelper.setText(body);
                    javaMailSender.send(mimeMessage);
                } catch (MessagingException e) {
                    System.out.println(e);
                } finally {
                    javaMailSender.send(mimeMessage);
                }
            }
        });
    }
}