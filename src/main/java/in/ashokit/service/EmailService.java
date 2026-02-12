package in.ashokit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(String to, String subject, String body) {
        try {
            mailSender.createMimeMessage(message -> {
                message.setRecipients(Message.RecipientType.TO, to);
                message.setSubject(subject);
                message.setText(body, true);
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
