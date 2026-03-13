package AngeMireille.library_management_system.repository;

import AngeMireille.library_management_system.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookRepo extends JpaRepository<Book, Integer> {
    List<Book> findAll(Sort sort);
    Page<Book> findAll(Pageable pageable);
    Page<Book> findByAuthors_Authorid(int authorId, Pageable pageable);
    boolean existsByTitleAndAuthors_Authorid(String title, int authorId);
    
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title%")
    Page<Book> searchByTitle(@Param("title") String title, Pageable pageable);
}