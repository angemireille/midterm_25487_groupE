package AngeMireille.library_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import AngeMireille.library_management_system.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
public interface CategoryRepo extends JpaRepository<Category, Integer> {
List<Category> findAll(Sort sort);
Page<Category> findAll(Pageable pageable);
boolean existsByName(String name);
}
