package modulepost.modulepost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling

public class ModulePostApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModulePostApplication.class, args);
    }

}
