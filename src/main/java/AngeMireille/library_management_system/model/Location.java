package AngeMireille.library_management_system.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "locations")
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(unique = true)
    private String code;
    
    private String name;
    
    @Enumerated(EnumType.STRING)
    private ELocationType type;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private Location parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Location> children;
    
    
    public Location() {}
    
    public Location(String code, String name, ELocationType type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public ELocationType getType() { return type; }
    public void setType(ELocationType type) { this.type = type; }
    
    public Location getParent() { return parent; }
    public void setParent(Location parent) { this.parent = parent; }
    
    public List<Location> getChildren() { return children; }
    public void setChildren(List<Location> children) { this.children = children; }
}