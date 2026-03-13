package AngeMireille.library_management_system.service;

import java.util.List;

import AngeMireille.library_management_system.dto.CategoryDTO;
import AngeMireille.library_management_system.dto.CategorySaveDTO;
import AngeMireille.library_management_system.dto.CategoryUpdateDTO;

public interface CategoryService {
    String addCategory(CategorySaveDTO categorySaveDTO);

    List<CategoryDTO> getAllCategory();

    String updateCategory(CategoryUpdateDTO categoryUpdateDTO);

    String deleteCategory(int id);
}
