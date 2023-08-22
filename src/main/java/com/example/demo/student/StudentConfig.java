package com.example.demo.student;

import java.time.LocalDate;

import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StudentConfig {
    
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository){
        return args ->{
          	Student Ghifari = new Student("Ghifari", "mar.ghifari@gmail.com", LocalDate.of(2001, 11, 8));
          	Student Reza = new Student("Reza", "mar.reza@gmail.com", LocalDate.of(2003, 4, 12));

            repository.saveAll(
                List.of(Ghifari, Reza)
            );

        };
    }
}
