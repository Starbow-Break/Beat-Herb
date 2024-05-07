package store.beatherb.restapi.global.mail.service;
import store.beatherb.restapi.global.mail.vo.MailVo;

public interface MailService {
    void authMailSend(MailVo mailVo);
}
