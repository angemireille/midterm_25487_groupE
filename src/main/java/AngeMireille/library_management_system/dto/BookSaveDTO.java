package AngeMireille.library_management_system.dto;

import java.util.Set;

public class BookSaveDTO {
    private String title;
    private Set<Integer> authorIds;
    private int category_id;
    private String language;
    private int totalCopies;
    private int availableCopies;

    public BookSaveDTO() {}

    public BookSaveDTO(String title, Set<Integer> authorIds, int category_id, 
                       String language, int totalCopies, int availableCopies) {
        this.title = title;
        this.authorIds = authorIds;
        this.category_id = category_id;
        this.language = language;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public Set<Integer> getAuthorIds() { return authorIds; }
    public void setAuthorIds(Set<Integer> authorIds) { this.authorIds = authorIds; }
    
    public int getCategory_id() { return category_id; }
    public void setCategory_id(int category_id) { this.category_id = category_id; }
    
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int availableCopies) { this.availableCopies = availableCopies; }
}