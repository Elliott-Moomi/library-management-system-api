package library.management.system.service;

import jakarta.persistence.EntityNotFoundException;
import library.management.system.domain.Book;
import library.management.system.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book getBook(UUID id) {
        return bookRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Book with id: " + id + " does not exist"));

    }

    @Transactional
    public Book addBook(final Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(final UUID id, final Book updatedBook) {
        final var book = getBook(id);

        book.setTitle(updatedBook.getTitle());
        book.setIsbn(updatedBook.getIsbn());
        book.setAuthor(updatedBook.getAuthor());
        book.setQuantity(updatedBook.getQuantity());
        book.setPublicationDate(updatedBook.getPublicationDate());
        bookRepository.save(book);

        return book;
    }

    @Transactional
    public void removeBook(final UUID id) {
        final var book = getBook(id);
        bookRepository.delete(book);
    }
}
