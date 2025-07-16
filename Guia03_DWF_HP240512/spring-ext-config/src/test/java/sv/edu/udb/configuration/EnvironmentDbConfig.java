package sv.edu.udb.configuration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;            //Bryan Steven Hernandez Polio HP240512
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.Objects;
@Getter
@Configuration
public class EnvironmentDbConfig {
    private final String dbDriver;
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;
    public EnvironmentDbConfig(final @Value("${db.driver}") String dbDriver,
                               final @Value("${db.url}") String dbUrl,
                               final @Value("${db.username}") String dbUsername,
                               final @Value("${db.password}") String dbPassword) {
        this.dbDriver = Objects.requireNonNull(dbDriver);
        this.dbUrl = Objects.requireNonNull(dbUrl);
        this.dbUsername = Objects.requireNonNull(dbUsername);
        this.dbPassword = Objects.requireNonNull(dbPassword);
    }
    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName(this.dbDriver)
                .url(this.dbUrl)
                .username(this.dbUsername)
                .password(this.dbPassword)
                .build();
    }
}