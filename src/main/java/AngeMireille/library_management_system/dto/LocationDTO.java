package AngeMireille.library_management_system.dto;

import AngeMireille.library_management_system.model.ELocationType;
import java.util.UUID;

public class LocationDTO {
    private UUID id;
    private String code;
    private String name;
    private ELocationType type;
    private UUID parentId;
    private String parentName;
    
    public LocationDTO() {}
    
    public LocationDTO(UUID id, String code, String name, ELocationType type, UUID parentId, String parentName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.parentName = parentName;
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
    
    public UUID getParentId() { return parentId; }
    public void setParentId(UUID parentId) { this.parentId = parentId; }
    
    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }
}