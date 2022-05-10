package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){

        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getBook(){
        return bookService.getBook();
    }

    @PostMapping
    public void registerNewBook(@RequestBody Book book){
        bookService.addBook(book);
    }

    @DeleteMapping(path = "{id}")
    public void deleteBook(@PathVariable("id") Long id){
        bookService.deleteBook(id);
    }

    @PutMapping(path = "{bookName}")
    public void issueBook(@PathVariable("bookName") String bookName){
        bookService.issueBook(bookName);
    }
}
