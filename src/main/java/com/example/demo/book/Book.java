package com.example.demo.book;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Entity
@Table
public class Book {
    // initializing variables
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    private Long id;
    private String bookName;
    private Integer bookQuantity;
//    private static List<String> bookStatus = Arrays.asList("issued", "in-stock", "out-of-stock");
    private String bookStatus;

    private Integer bookIssuedCount;


    public Book(){

    }
    public Book(Long id, String bookName, Integer bookQuantity, String bookStatus, Integer bookIssuedCount){
        this.id = id;
        this.bookName = bookName;
        this.bookQuantity = bookQuantity;
        this.bookStatus = bookStatus;
        this.bookIssuedCount = bookIssuedCount;

    }
    public Book(String bookName, Integer bookQuantity, String bookStatus, Integer bookIssuedCount){
        this.bookName = bookName;
        this.bookQuantity = bookQuantity;
        this.bookStatus = bookStatus;
        this.bookIssuedCount = bookIssuedCount;
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getBookQuantity() {
        return bookQuantity;
    }

    public void setBookQuantity(Integer bookQuantity) {
        this.bookQuantity = bookQuantity;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Integer getBookIssuedCount(){
        return this.bookIssuedCount = bookIssuedCount;
    }
    public void setBookIssuedCount(Integer bookIssuedCount){
        this.bookIssuedCount = bookIssuedCount;
    }
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", bookQuantity=" + bookQuantity +
                ", bookStatus=" + bookStatus +
                ", bookIssuedCount=" + bookIssuedCount +
                '}';
    }
}
