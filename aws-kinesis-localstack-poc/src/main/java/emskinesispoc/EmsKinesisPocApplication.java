package emskinesispoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmsKinesisPocApplication {


    public static void main(String[] args) {
        System.setProperty("com.amazonaws.sdk.disableCbor", "true");
        SpringApplication.run(EmsKinesisPocApplication.class, args);
    }
}
