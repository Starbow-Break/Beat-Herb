package store.beatherb.restapi.global.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import store.beatherb.restapi.global.mail.vo.MailVo;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{
    private final JavaMailSender javaMailSender;

    public void authMailSend(MailVo mailVo) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            message.setFrom(mailVo.getFROM_ADDRESS());
            message.setTo(mailVo.getAddress());
            message.setSubject(mailVo.getTitle());
            message.setText(mailVo.getMessage(), true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
