package AngeMireille.library_management_system.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;


@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "category_id", length = 11)
    private int categoryid;

    @Column(name= "name", length = 45)
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonBackReference("category-book")
    private Set<Book> books;

    public Category(int categoryid, String name) {
        this.categoryid = categoryid;
        this.name = name;
    }

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public int getcategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category [categoryid=" + categoryid + ", name=" + name + "]";
    }

    
}
