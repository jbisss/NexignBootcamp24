package ru.jbisss.cdrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.jbisss.cdrservice.cdrGenerator.CdrGenerator;
import ru.jbisss.cdrservice.repository.AbonentRepository;

@SpringBootApplication
public class CdrServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CdrServiceApplication.class, args);
        System.out.println(applicationContext.getBean(AbonentRepository.class).findAll());
        applicationContext.getBean(CdrGenerator.class).generateCdrs();
    }
}
