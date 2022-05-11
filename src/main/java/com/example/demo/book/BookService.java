package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public void addBook(Book book) {
        Optional<Book> bookInRepo = bookRepository.findBookBybookName(book.getBookName());
        //if book is present in the library, update bookQuantity on POST request
        String bookStatusInPayload = book.getBookStatus();
        Integer bookQuantityInPayload = book.getBookQuantity();
        if(!bookStatusInPayload.equals("in-stock") || bookQuantityInPayload <= 0){
            throw new IllegalStateException("invalid info noticed in the payload");
        } else{
            bookRepository.save(book);
        }
        if(bookInRepo.isPresent()){
            throw new IllegalStateException("the book is already available at the library!");
            }
        else{
            bookRepository.save(book);
        }
    }
    @Transactional
    public void deleteBook(Long id, Integer bookQuantity) {
        //boolean exists = bookRepository.existsById(id);
        //check if book exists, if not then throw exception.
        Book book = bookRepository.findBookById(id).orElseThrow(() -> new IllegalStateException(
                "book with id "+ id +" does not exist..."
        ));
        if(bookQuantity != null){
            if(bookQuantity > 0 && Objects.equals(book.getId(), id)){
                //if it does exist, then reduce the quantity by the amount provided, but ensure it doesnt go negative
                Integer oldBookQuant = book.getBookQuantity();
                Integer newBookQuant = oldBookQuant - bookQuantity;

                if(newBookQuant < 0){
                    throw new IllegalStateException("book quantity cant be less than 0!");
                } else if(newBookQuant == 0){
                    book.setBookQuantity(newBookQuant);
                    book.setBookStatus("out-of-stock");
                } else{
                    book.setBookQuantity(newBookQuant);
                }
            } else if(bookQuantity < 0){
                throw new IllegalStateException("invalid amount of book quantity provided!");
            }
        } else{
            bookRepository.deleteById(id);
        }
    }


    @Transactional
    public void issueBook(Long id, Integer bookQuantity) {
        //find if the book exists in library by name, if it doesnt, let the user know the same
        Book book = bookRepository.findBookById(id)
                .orElseThrow(() -> new IllegalStateException(
                "book with ID "+ id +" does not exist in the library... :/"
        ));
        Integer bookQuantInRepo = book.getBookQuantity();
        Integer bookQuantAfterIssue = bookQuantInRepo - bookQuantity;
        if(bookQuantity > 0 && bookQuantAfterIssue >= 0){
            book.setBookQuantity(bookQuantAfterIssue);
            if(bookQuantAfterIssue == 0){
                book.setBookStatus("out-of-stock");
            }
            book.setBookIssuedCount(bookQuantity);
        } else{
            throw new IllegalStateException("invalid book quantity supplied!");
        }
        if(bookQuantity <= 0){
            throw new IllegalStateException("provide a valid quantity to issue!");
        }
        //check if the name supplied by the user is valid.
    }

    @Transactional
    public void updateBookQuant(Long id, Integer bookQuantity) {
        Book book = bookRepository.findBookById(id).orElseThrow(() -> new IllegalStateException(
                "book with id "+ id +" does not exist. Cannot update item."
        ));
        Integer oldBookQuant = book.getBookQuantity();

        if(bookQuantity < 0){
            System.out.println("udpate - old book quant from repo: "+oldBookQuant);
            Integer newBookQuant = oldBookQuant - Math.abs(bookQuantity);
            System.out.println("new book quant from repo: "+newBookQuant);
            if(newBookQuant < 0){
                throw new IllegalStateException(
                        "attempt to remove more books than in the inventory failed!");
            } else if(newBookQuant == 0){
                book.setBookQuantity(newBookQuant);
                book.setBookStatus("out-of-stock");
            }else if(newBookQuant > 0){
                System.out.println("here!");
                book.setBookQuantity(newBookQuant);
                book.setBookStatus("in-stock");
            }
            //updating with a positive value adds the new quant to the repo
        } else {
            Integer newBookQuant = oldBookQuant + bookQuantity;
            System.out.println("update + new book quant from repo: "+newBookQuant);
            if (newBookQuant == 0) {
                book.setBookQuantity(newBookQuant);
                book.setBookStatus("out-of-stock");
            } else if (newBookQuant > 0) {
                book.setBookQuantity(newBookQuant);
                book.setBookStatus("in-stock");
            } else {
                throw new IllegalStateException("book quantity cant be less than 0!");
            }
        }
    }
}
