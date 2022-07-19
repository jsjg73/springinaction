package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TacoCloudApplication {

	public static void main(String[] args) {
		// git config email 설정 변경 테스트를 위한 주석.
		SpringApplication.run(TacoCloudApplication.class, args);
	}

}
