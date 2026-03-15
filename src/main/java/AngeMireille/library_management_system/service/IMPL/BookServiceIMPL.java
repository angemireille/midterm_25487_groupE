package AngeMireille.library_management_system.service.IMPL;

import AngeMireille.library_management_system.dto.BookDTO;
import AngeMireille.library_management_system.dto.BookSaveDTO;
import AngeMireille.library_management_system.model.Author;
import AngeMireille.library_management_system.model.Book;
import AngeMireille.library_management_system.model.Category;
import AngeMireille.library_management_system.repository.AuthorRepo;
import AngeMireille.library_management_system.repository.BookRepo;
import AngeMireille.library_management_system.repository.CategoryRepo;
import AngeMireille.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceIMPL implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private AuthorRepo authorRepo;

    @Autowired
    private CategoryRepo categoryRepo;

  @Override
public BookDTO addBook(BookDTO bookDTO) {
    Set<Author> authors = new HashSet<>();
    for (Author author : bookDTO.getAuthors()) {
        Author existingAuthor = authorRepo.findById(author.getAuthorid())
                .orElseThrow(() -> new RuntimeException("Author not found"));
        authors.add(existingAuthor);
    }
    
    Category category = categoryRepo.findById(bookDTO.getCategory().getcategoryid())
            .orElseThrow(() -> new RuntimeException("Category not found"));

    Book book = new Book(
            bookDTO.getTitle(),
            authors,
            category,
            bookDTO.getLanguage(),
            bookDTO.getTotalCopies(),
            bookDTO.getAvailableCopies()
    );
    Book saved = bookRepo.save(book);
    return toDTO(saved);
}

public BookDTO addBook(BookSaveDTO bookSaveDTO) {
    Set<Author> authors = new HashSet<>();
    for (Integer authorId : bookSaveDTO.getAuthorIds()) {
        Author existingAuthor = authorRepo.findById(authorId)
                .orElseThrow(() -> new RuntimeException("Author not found with ID: " + authorId));
        authors.add(existingAuthor);
    }
    
    Category category = categoryRepo.findById(bookSaveDTO.getCategory_id())
            .orElseThrow(() -> new RuntimeException("Category not found with ID: " + bookSaveDTO.getCategory_id()));

    Book book = new Book(
            bookSaveDTO.getTitle(),
            authors,
            category,
            bookSaveDTO.getLanguage(),
            bookSaveDTO.getTotalCopies(),
            bookSaveDTO.getAvailableCopies()
    );
    Book saved = bookRepo.save(book);
    return toDTO(saved);
}

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public BookDTO updateBook(int bookid, BookDTO bookDTO) {
        Optional<Book> opt = bookRepo.findById(bookid);
        if (opt.isPresent()) {
            Book book = opt.get();
            Set<Author> authors = new HashSet<>();
            for (Author author : bookDTO.getAuthors()) {
                Author existingAuthor = authorRepo.findById(author.getAuthorid())
                        .orElseThrow(() -> new RuntimeException("Author not found"));
                authors.add(existingAuthor);
            }
            Category category = categoryRepo.findById(bookDTO.getCategory().getcategoryid())
                    .orElseThrow(() -> new RuntimeException("Category not found"));

            book.setTitle(bookDTO.getTitle());
            book.setAuthors(authors);
            book.setCategory(category);
            book.setLanguage(bookDTO.getLanguage());
            book.setTotalCopies(bookDTO.getTotalCopies());
            book.setAvailableCopies(bookDTO.getAvailableCopies());
            return toDTO(bookRepo.save(book));
        }
        return null;
    }

    @Override
    public boolean deleteBook(int bookid) {
        if (bookRepo.existsById(bookid)) {
            bookRepo.deleteById(bookid);
            return true;
        }
        return false;
    }

    @Override
    public BookDTO getBookById(int bookid) {
        return bookRepo.findById(bookid).map(this::toDTO).orElse(null);
    }

    private BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getBookid(),
                book.getTitle(),
                book.getAuthors(),
                book.getCategory(),
                book.getLanguage(),
                book.getTotalCopies(),
                book.getAvailableCopies()
        );
    }
    @Override
public Page<BookDTO> getAllBooksPaginated(Pageable pageable) {
    return bookRepo.findAll(pageable).map(this::toDTO);
}

@Override
public List<BookDTO> getAllBooksSorted(Sort sort) {
    return bookRepo.findAll(sort).stream().map(this::toDTO).collect(Collectors.toList());
}

@Override
public Page<BookDTO> getBooksByAuthorPaginated(int authorId, Pageable pageable) {
    return bookRepo.findByAuthors_Authorid(authorId, pageable).map(this::toDTO);
}

@Override
public Page<BookDTO> searchBooksByTitle(String title, Pageable pageable) {
    return bookRepo.searchByTitle(title, pageable).map(this::toDTO);
}

}