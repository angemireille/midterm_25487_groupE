package AngeMireille.library_management_system.service.IMPL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import AngeMireille.library_management_system.controller.CategoryController;
import AngeMireille.library_management_system.dto.CategoryDTO;
import AngeMireille.library_management_system.dto.CategorySaveDTO;
import AngeMireille.library_management_system.dto.CategoryUpdateDTO;
import AngeMireille.library_management_system.model.Category;
import AngeMireille.library_management_system.repository.CategoryRepo;
import AngeMireille.library_management_system.service.CategoryService;

@Service
public class CategoryServiceIMPL implements CategoryService {

    @SuppressWarnings("unused")
    private final CategoryController categoryController;

    @Autowired
    private CategoryRepo categoryRepo;

    CategoryServiceIMPL(CategoryController categoryController) {
        this.categoryController = categoryController;
    }

    @Override
    public String addCategory(CategorySaveDTO categorySaveDTO) {

        Category category = new Category(
            categorySaveDTO.getName()
        );
        categoryRepo.save(category);
        return category.getName();
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        
        List<Category> getCategorys = categoryRepo.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(Category category : getCategorys)
        {
            CategoryDTO categoryDTO = new CategoryDTO(
                category.getcategoryid(),
                category.getName()
            );
            categoryDTOList.add(categoryDTO);
        }
       return categoryDTOList;
    }

    @Override
    public String updateCategory(CategoryUpdateDTO categoryUpdateDTO) {
        
        if (categoryRepo.existsById(categoryUpdateDTO.getCategoryid())) {
            @SuppressWarnings("deprecation")
            Category category = categoryRepo.getById(categoryUpdateDTO.getCategoryid());
            category.setName(categoryUpdateDTO.getName());

            categoryRepo.save(category);
            return category.getName();
        
        } else {
            System.out.println("Category ID doesn't Exist!");
        } 

            return null;
    }

    @Override
    public String deleteCategory(int id) {
        
        if(categoryRepo.existsById(id))
        {
            categoryRepo.deleteById(id);
        }
        else
        { 
            System.out.println("ID Not Found!");
        }

        return null;
    }
}

