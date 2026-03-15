package AngeMireille.library_management_system.controller;

import AngeMireille.library_management_system.dto.BookDTO;
import AngeMireille.library_management_system.dto.BookSaveDTO;
import AngeMireille.library_management_system.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<BookDTO> addBook(@RequestBody BookSaveDTO bookSaveDTO) {
        return ResponseEntity.ok(bookService.addBook(bookSaveDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") int id) {
        BookDTO book = bookService.getBookById(id);
        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") int id, @RequestBody BookDTO bookDTO) {
        BookDTO updated = bookService.updateBook(id, bookDTO);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable("id") int id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok().body("Book deleted");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/paginated")
public ResponseEntity<Page<BookDTO>> getBooksPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "title") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
    
    Sort sort = direction.equalsIgnoreCase("asc") 
        ? Sort.by(sortBy).ascending() 
        : Sort.by(sortBy).descending();
    
    Pageable pageable = PageRequest.of(page, size, sort);
    return ResponseEntity.ok(bookService.getAllBooksPaginated(pageable));
}

@GetMapping("/sorted")
public ResponseEntity<List<BookDTO>> getBooksSorted(
        @RequestParam(defaultValue = "title") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
    
    Sort sort = direction.equalsIgnoreCase("asc") 
        ? Sort.by(sortBy).ascending() 
        : Sort.by(sortBy).descending();
    
    return ResponseEntity.ok(bookService.getAllBooksSorted(sort));
}

@GetMapping("/by-author/{authorId}/paginated")
public ResponseEntity<Page<BookDTO>> getBooksByAuthorPaginated(
        @PathVariable int authorId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(bookService.getBooksByAuthorPaginated(authorId, pageable));
}

@GetMapping("/search")
public ResponseEntity<Page<BookDTO>> searchBooks(
        @RequestParam String title,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    
    Pageable pageable = PageRequest.of(page, size);
    return ResponseEntity.ok(bookService.searchBooksByTitle(title, pageable));
}
}