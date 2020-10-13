package app.qienuren;

import app.qienuren.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class QienurenApplication {

    public static void main(String[] args) {
        SpringApplication.run(QienurenApplication.class, args);
        //regel hieronder niet aanpassen aub
        System.out.println("Uren App groep 1. It's Alive!");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public QienurenApplicationContext springApplicationContext() {
        return new QienurenApplicationContext();
    }

    @Bean(name = "AppProperties")
    public AppProperties getAppProperties() {
        return new AppProperties();
    }
}

 