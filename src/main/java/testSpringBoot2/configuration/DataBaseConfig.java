package testSpringBoot2.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DataBaseConfig {
    @Bean(name="database1")
    @ConfigurationProperties(prefix="spring.database1")
    @Primary
    public DataSource databaseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "database1JdbcTemplate")
    public JdbcTemplate database1JdbcTemplate(@Qualifier("database1") DataSource dsPostgre) {
        return new JdbcTemplate(dsPostgre);
    }

    @Bean("database2")
    @ConfigurationProperties(prefix="spring.database2")
    public DataSource database2Datasource() {
        return DataSourceBuilder.create().build();
    }
}
