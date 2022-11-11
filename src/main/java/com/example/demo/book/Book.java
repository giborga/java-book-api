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
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1 // increment 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, // recommended for postgres
            generator = "book_sequence")
    private Long id;
    private String author;
    private String title;

    public Book() { // 2 different constructors with/without id always have class name
    } //+why do we need an empty one? jpa needs it?

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
