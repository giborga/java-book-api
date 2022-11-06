// save to database
// connected to
package com.example.demo.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration // conf file
public class BookConfig {

    // bean to access book repo
    @Bean
    CommandLineRunner commandLineRunner(BookRepository repository) { // provides access to repository
        return args -> { // + here works instead of POST?
            Book book1 = new Book( // remove id - it will be generated
                    "Fyodor Dostoevsky",
                    "Military operation and Peace");
            Book book2 = new Book(
                    "Charles Baudelaire",
                    "Le Voyage");
            Book book3 = new Book(
                    "Haruki Murakami",
                    "The end of the world");
            Book book4 = new Book(
                    "Shota Rustaveli",
                    "The man in the panthers skin");
repository.saveAll(List.of(book1, book2, book3, book4)); // save to database
        };
    }
}