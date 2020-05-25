package testSpringBoot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import testSpringBoot2.component.Component1;

@SpringBootApplication
@EnableConfigurationProperties(Component1.class)
public class MainClass {
    public static void main(String[] arg) {

        SpringApplication.run(MainClass.class);
    }
}
