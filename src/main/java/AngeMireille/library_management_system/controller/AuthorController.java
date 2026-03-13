package AngeMireille.library_management_system.controller;

import java.util.List;
import AngeMireille.library_management_system.service.IMPL.AuthorServiceIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AngeMireille.library_management_system.dto.AuthorDTO;
import AngeMireille.library_management_system.dto.AuthorSaveDTO;
import AngeMireille.library_management_system.dto.AuthorUpdateDTO;
import AngeMireille.library_management_system.repository.AuthorRepo;
import AngeMireille.library_management_system.service.AuthorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@CrossOrigin
@RequestMapping("/api/author")
public class AuthorController {

    @SuppressWarnings("unused")
    private final AuthorServiceIMPL authorServiceIMPL;
     
    @SuppressWarnings("unused")
    private final AuthorRepo authorRepo;

    @Autowired
    private AuthorService authorService;

    AuthorController( AuthorRepo authorRepo, @Lazy AuthorServiceIMPL authorServiceIMPL) {
        this.authorRepo = authorRepo;
        this.authorServiceIMPL = authorServiceIMPL;
    }
    
    @PostMapping(path = "/save")
    public String saveAuthor(@RequestBody AuthorSaveDTO authorSaveDTO)
    {
        @SuppressWarnings("unused")
        String authorname = authorService.addAuthor(authorSaveDTO);
        return "Added Successfully";
    }

    @GetMapping(path = "/getAllAuthor")
    public List<AuthorDTO> getAllAuthor()
    {
        List<AuthorDTO> allAuthors = authorService.getAllAuthor();
        return allAuthors;
    }
    
    @PutMapping(path = "/update")
    public String updateAuthor(@RequestBody AuthorUpdateDTO authorUpdateDTO)
    {
        @SuppressWarnings("unused")
        String authorname = authorService.updateAuthor(authorUpdateDTO);
        return "Updated Successfully!";
    }

    @DeleteMapping(path = "/delete/{id}")
    public String deleteAuthor(@PathVariable(value = "id")int id)
    {
        @SuppressWarnings("unused")
        String authorname = authorService.deleteAuthor(id);
        return "Deleted Successfully!";
    }

}
