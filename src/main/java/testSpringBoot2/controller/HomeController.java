package testSpringBoot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

@RestController
public class HomeController {

    private DataSource database1;
    private DataSource database2;
    private DataSource database3;
    private JdbcTemplate postgresTemplate;

    @Value("${titit.titi: fff}")
    private String titi;

    @Autowired
    public HomeController(
            //@Qualifier("database1") DataSource database1,
            DataSource database1,
            DataSource database2,
            DataSource database3,
            @Qualifier("database1JdbcTemplate") JdbcTemplate postgresTemplate) {

        this.database1 = database1;
        this.database2 = database2;
        this.database3 = database3;
        this.postgresTemplate = postgresTemplate;
    }

    @GetMapping(path="/home")
    public String home() {
        return "Home";
    }
}
