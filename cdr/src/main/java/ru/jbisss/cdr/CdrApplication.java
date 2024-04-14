package ru.jbisss.cdr;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.jbisss.cdr.db.repository.AbonentRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class CdrApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(CdrApplication.class, args);
        System.out.println(applicationContext.getBean(AbonentRepository.class).findAll());
        System.out.println("321re12");
    }
}
