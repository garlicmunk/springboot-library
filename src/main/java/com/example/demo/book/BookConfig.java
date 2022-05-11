package com.example.demo.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BookConfig {

    @Bean
    CommandLineRunner commandLineRunner(BookRepository repository){
        return args -> {
            Book mockingbird = new Book(
                    "mockingbird",
                    1,
                    "in-stock",
                    0
                    );

            Book artOfDeception = new Book(
                    "art-of-deception",
                    1,
                    "in-stock",
                    0
            );

            Book blackHatPython= new Book(
                    "black-hat-python",
                    2,
                    "in-stock",
                    0
            );

            repository.saveAll(
                    List.of(mockingbird, artOfDeception, blackHatPython)
            );

        };
    }
}
