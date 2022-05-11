package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService){

        this.bookService = bookService;
    }

    @GetMapping(path = "show-inventory")
    public List<Book> getBook(){
        return bookService.getBook();
    }

    @PostMapping(path = "add-book")
    public void registerNewBook(@RequestBody Book book){
        bookService.addBook(book);
    }

    @DeleteMapping(path = {"{id}/delete", "{id}/delete-by/{bookQuantity}" })
    public void deleteBook(@PathVariable("id") Long id, @PathVariable(value = "bookQuantity", required = false) Integer bookQuantity){
        bookService.deleteBook(id, bookQuantity);
    }

    @PutMapping(path = "{id}/issue/{bookQuantity}")
    public void issueBook(@PathVariable("id") Long id,@PathVariable("bookQuantity") Integer bookQuantity){
        bookService.issueBook(id, bookQuantity);
    }

    @PutMapping(path = "{id}/update-by/{bookQuantity}")
    public void updateBook(@PathVariable("id") Long id, @PathVariable("bookQuantity") Integer bookQuantity){
        bookService.updateBookQuant(id, bookQuantity);
    }
}
