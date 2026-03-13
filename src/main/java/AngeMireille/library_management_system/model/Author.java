package AngeMireille.library_management_system.model;

import java.util.Set;
import jakarta.persistence.*;
import java.util.HashSet;

@Entity
@Table(name="author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "author_id", length = 11)
    private int authorid;

    @Column(name= "name", length = 45)
    private String name;

    @ManyToMany(mappedBy = "authors")
    private Set<Book> booksManyToMany = new HashSet<>();

    public Author(int authorid, String name) {
        this.authorid = authorid;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }

    public Author() {
    }

    public int getAuthorid() {
        return authorid;
    }

    public void setAuthorid(int authorid) {
        this.authorid = authorid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author [authorid=" + authorid + ", name=" + name + "]";
    }
    
    public Set<Book> getBooksManyToMany() { 
        return booksManyToMany; 
    }
    
    public void setBooksManyToMany(Set<Book> booksManyToMany) { 
        this.booksManyToMany = booksManyToMany; 
    }
}
