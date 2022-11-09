// creates class Books, its variables, constructors, getters and setters, string override

package com.example.demo.book;

import javax.persistence.*;

//use spring data jpa
@Entity // hibernate
@Table // map Book class to a table in database
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

    public Book(String author, // dont need id - it will be generated
                   String title) {
        this.author = author; // don't need id here - variable is already assigned in SequenceGenerator
        this.title = title;
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    } // + why do we need to write it again? why not just title?

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
