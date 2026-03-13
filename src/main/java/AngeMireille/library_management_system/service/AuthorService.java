package AngeMireille.library_management_system.service;

import java.util.List;

import AngeMireille.library_management_system.dto.AuthorDTO;
import AngeMireille.library_management_system.dto.AuthorSaveDTO;
import AngeMireille.library_management_system.dto.AuthorUpdateDTO;

public interface AuthorService {

    String addAuthor(AuthorSaveDTO authorSaveDTO);

    List<AuthorDTO> getAllAuthor();

    String updateAuthor(AuthorUpdateDTO authorUpdateDTO);

    String deleteAuthor(int id);

    
} 
