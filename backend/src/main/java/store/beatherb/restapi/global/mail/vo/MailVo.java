package store.beatherb.restapi.global.mail.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
public class MailVo {
    private final String FROM_ADDRESS = "beatherb.store@gmail.com";
    private String address;
    private String uuid;
    private String title = "[BeatHerb] 메일 로그인 인증";
    private String message;

    @Builder
    public MailVo(String address,String uuid) {
        this.address = address;
        this.uuid = uuid;
        this.message = "<html>\n" +
                "<body>\n" +
                "    <div style=\"width: 500px; margin: auto; background-color: #070707\">\n" +
                "      <div\n" +
                "        style=\"\n" +
                "          height: 80px;\n" +
                "          border-bottom: 4px solid #b5cc18;\n" +
                "          display: flex;\n" +
                "          justify-content: center;\n" +
                "          align-items: center;\n" +
                "          color: #f5f5f5;\n" +
                "        \"\n" +
                "      >\n" +
                "        BeatHerb 인증 메일\n" +
                "      </div>\n" +
                "      <div\n" +
                "        style=\"\n" +
                "          height: 300px;\n" +
                "          display: flex;\n" +
                "          flex-direction: column;\n" +
                "          justify-content: space-evenly;\n" +
                "          padding: 15px;\n" +
                "          color: #f5f5f5;\n" +
                "        \"\n" +
                "      >\n" +
                "        <div>아래 링크를 눌러 메일 인증을 완료하세요.</div>\n" +
                "        <div>\n" +
                "          <a href=\"https://beatherb.store/auth_email/redirect?token=" + this.uuid +"\"> https://beatherb.store/auth_email/redirect?token=" + this.uuid +"</a>\n" +
                "        </div>\n" +
                "        <div></div>\n" +
                "        <div style=\"display: flex; justify-content: center\">\n" +
                "          <a href=\"https://beatherb.store/auth_email/redirect?token="+ this.uuid + "\">\n" +
                "            <button\n" +
                "              type=\"button\"\n" +
                "              style=\"\n" +
                "                color: #f5f5f5;\n" +
                "                height: 50px;\n" +
                "                width: 100px;\n" +
                "                background-color: #b5cc18;\n" +
                "                border-radius: 10px;\n" +
                "                border: none;\n" +
                "              \"\n" +
                "            >\n" +
                "              인증완료\n" +
                "            </button>\n" +
                "          </a>\n" +
                "        </div>\n" +
                "      </div>\n" +
                "      <div\n" +
                "        style=\"\n" +
                "          height: 120px;\n" +
                "          background-color: #202020;\n" +
                "          display: flex;\n" +
                "          align-items: center;\n" +
                "          color: #dddddd;\n" +
                "          padding: 15px;\n" +
                "        \"\n" +
                "      >\n" +
                "        만약 본인이 요청한 것이 아니라면 본 메일을 무시해주세요.\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </body>" +
                "</html>";
    }
}
