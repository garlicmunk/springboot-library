package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    public List<Book> getBook(){
        //fetch the book in library
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        Optional<Book> bookOptional = bookRepository.findBookBybookName(book.getBookName());
        //if book is present in the library, update bookQuantity on POST request
        if(bookOptional.isPresent()){
//            Integer updatedQuant = book.getBookQuantity() + 1;
//            book.setBookQuantity(updatedQuant);
            throw new IllegalStateException("the book is already available at the library!");
        }
        else{
            bookRepository.save(book);
        }
        //else add new book to library

        System.out.println(book);
    }

    public void deleteBook(Long id) {
        boolean exists = bookRepository.existsById(id);
        if(!exists){
            throw new IllegalStateException("book with id "+ id +" does not exist...");
        }
        bookRepository.deleteById(id);

    }


    @Transactional
    public void issueBook(String bookName) {
        //find if the book exists in library by name, if it doesnt, let the user know the same
        Book book = bookRepository.findBookBybookName(bookName)
                .orElseThrow(() -> new IllegalStateException(
                "book with name "+bookName+" does not exist in the library... :/"
        ));
        //check if the name supplied by the user is valid.
        if(bookName != null && bookName.length() > 0 && Objects.equals(book.getBookName(), bookName)){
            Integer oldBookQuant = book.getBookQuantity();
            Integer newBookQuant = oldBookQuant - 1;

            book.setBookName(bookName);
            book.setBookQuantity(newBookQuant);
            book.setBookStatus("issued");
        }
    }
}
