package comms.folder.comms_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CommsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommsServiceApplication.class, args);
	}

}
