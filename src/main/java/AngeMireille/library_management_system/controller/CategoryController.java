package AngeMireille.library_management_system.controller;

import java.util.List;
import AngeMireille.library_management_system.service.IMPL.CategoryServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AngeMireille.library_management_system.dto.CategoryDTO;
import AngeMireille.library_management_system.dto.CategorySaveDTO;
import AngeMireille.library_management_system.dto.CategoryUpdateDTO;
import AngeMireille.library_management_system.repository.CategoryRepo;
import AngeMireille.library_management_system.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin
@RequestMapping("/api/category")
public class CategoryController {

    @SuppressWarnings("unused")
    private final CategoryServiceIMPL categoryServiceIMPL;
     
    @SuppressWarnings("unused")
    private final CategoryRepo categoryRepo;

    @Autowired
    private CategoryService categoryService;

    CategoryController( CategoryRepo categoryRepo, @Lazy CategoryServiceIMPL categoryServiceIMPL) {
        this.categoryRepo = categoryRepo;
        this.categoryServiceIMPL = categoryServiceIMPL;
    }
    
    @PostMapping(path = "/save")
    public String saveCategory(@RequestBody CategorySaveDTO categorySaveDTO)
    {
        @SuppressWarnings("unused")
        String categoryname = categoryService.addCategory(categorySaveDTO);
        return "Added Successfully";
    }

    @GetMapping(path = "/getAllCategory")
    public List<CategoryDTO> getAllCategory()
    {
        List<CategoryDTO> allCategorys = categoryService.getAllCategory();
        return allCategorys;
    }
    
    @PutMapping(path = "/update")
    public String updateCategory(@RequestBody CategoryUpdateDTO categoryUpdateDTO)
    {
        @SuppressWarnings("unused")
        String categoryname = categoryService.updateCategory(categoryUpdateDTO);
        return "Updated Successfully!";
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteCategory(@PathVariable(value = "id")int id)
    {
        @SuppressWarnings("unused")
        String categoryname = categoryService.deleteCategory(id);
        return "Deleted Successfully!";
    }

}
