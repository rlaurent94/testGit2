package testSpringBoot2.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import testSpringBoot2.component.Component1;

@Component
public class Startup implements CommandLineRunner {

    Component1 component1;

    @Autowired
    public Startup(Component1 component1) {
        this.component1 = component1;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome " + component1.getNom());
    }
}
