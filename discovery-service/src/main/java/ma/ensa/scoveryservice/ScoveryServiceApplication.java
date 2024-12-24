package ma.ensa.scoveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ScoveryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoveryServiceApplication.class, args);
    }

}
