package AngeMireille.library_management_system.service;

import AngeMireille.library_management_system.dto.BookDTO;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
public interface BookService {
    BookDTO addBook(BookDTO bookDTO);
    List<BookDTO> getAllBooks();
    BookDTO updateBook(int bookid, BookDTO bookDTO);
    boolean deleteBook(int bookid);
    BookDTO getBookById(int bookid);
    Page<BookDTO> getAllBooksPaginated(Pageable pageable);
    List<BookDTO> getAllBooksSorted(Sort sort);
    Page<BookDTO> getBooksByAuthorPaginated(int authorId, Pageable pageable);
    Page<BookDTO> searchBooksByTitle(String title, Pageable pageable);
}
