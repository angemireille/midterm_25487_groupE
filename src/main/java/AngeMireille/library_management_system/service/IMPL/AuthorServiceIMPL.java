package AngeMireille.library_management_system.service.IMPL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import AngeMireille.library_management_system.controller.AuthorController;
import AngeMireille.library_management_system.dto.AuthorDTO;
import AngeMireille.library_management_system.dto.AuthorSaveDTO;
import AngeMireille.library_management_system.dto.AuthorUpdateDTO;
import AngeMireille.library_management_system.model.Author;
import AngeMireille.library_management_system.repository.AuthorRepo;
import AngeMireille.library_management_system.service.AuthorService;

@Service
public class AuthorServiceIMPL implements AuthorService {

    @SuppressWarnings("unused")
    private final AuthorController authorController;

    @Autowired
    private AuthorRepo authorRepo;

    AuthorServiceIMPL(AuthorController authorController) {
        this.authorController = authorController;
    }

    @Override
    public String addAuthor(AuthorSaveDTO authorSaveDTO) {

        Author author = new Author(
            authorSaveDTO.getName()
        );
        authorRepo.save(author);
        return author.getName();
    }

    @Override
    public List<AuthorDTO> getAllAuthor() {
        
        List<Author> getAuthors = authorRepo.findAll();
        List<AuthorDTO> authorDTOList = new ArrayList<>();

        for(Author author : getAuthors)
        {
            AuthorDTO authorDTO = new AuthorDTO(
                author.getAuthorid(),
                author.getName()
            );
            authorDTOList.add(authorDTO);
        }
       return authorDTOList;
    }

    @Override
    public String updateAuthor(AuthorUpdateDTO authorUpdateDTO) {
        
        if (authorRepo.existsById(authorUpdateDTO.getAuthorid())) {
            @SuppressWarnings("deprecation")
            Author author = authorRepo.getById(authorUpdateDTO.getAuthorid());
            author.setName(authorUpdateDTO.getName());

            authorRepo.save(author);
            return author.getName();
        
        } else {
            System.out.println("Author ID doesn't Exist!");
        } 

            return null;
    }

    @Override
    public String deleteAuthor(int id) {
        
        if(authorRepo.existsById(id))
        {
            authorRepo.deleteById(id);
        }
        else
        { 
            System.out.println("ID Not Found!");
        }

        return null;
    }
}

