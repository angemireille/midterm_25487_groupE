package AngeMireille.library_management_system.dto;

import AngeMireille.library_management_system.model.Author;
import AngeMireille.library_management_system.model.Category;
import java.util.Set;

public class BookDTO {
    private int bookid;
    private String title;
    private Set<Author> authors;
    private Category category;
    private String language;
    private int totalCopies;
    private int availableCopies;

    public BookDTO() {}

    public BookDTO(int bookid, String title, Set<Author> authors, Category category, 
                   String language, int totalCopies, int availableCopies) {
        this.bookid = bookid;
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.language = language;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public int getBookid() { return bookid; }
    public void setBookid(int bookid) { this.bookid = bookid; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Set<Author> getAuthors() { return authors; }
    public void setAuthors(Set<Author> authors) { this.authors = authors; }
    
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}