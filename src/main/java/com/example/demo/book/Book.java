// creates class Books, its variables, constructors, getters and setters, string override

package com.example.demo.book;

import lombok.*;

import javax.persistence.*;

//use spring data jpa
@Data //get rid of boilerplate code
@Entity // hibernate
@Table // map Book class to a table in database
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor

public class Book {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private Long id;
    private String author;
    private String title;

    public Book() {
    }

    public Book(String author,
                String title) {
        this.author = author;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
