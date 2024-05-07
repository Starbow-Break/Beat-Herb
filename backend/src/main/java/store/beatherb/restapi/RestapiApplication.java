package store.beatherb.restapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {
		@Server(url = "https://node5.wookoo.shop/api", description = "테스트 서버 URL"),
		@Server(url = "http://localhost:8080/api", description = "로컬 서버 URL"),

})

@Slf4j
public class RestapiApplication {

	public static void main(String[] args) {
		log.info("hello world!");
		SpringApplication.run(RestapiApplication.class, args);
	}

}
