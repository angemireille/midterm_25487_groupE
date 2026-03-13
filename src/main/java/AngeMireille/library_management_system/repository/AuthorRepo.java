package AngeMireille.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import AngeMireille.library_management_system.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
   boolean existsByName(String name);
   List<Author> findAll(Sort sort);
   Page<Author> findAll(Pageable pageable);
}
