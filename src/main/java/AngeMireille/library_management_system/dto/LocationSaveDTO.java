package AngeMireille.library_management_system.dto;

import AngeMireille.library_management_system.model.ELocationType;

public class LocationSaveDTO {
    private String name;
    private String code;
    private ELocationType type;  
    private String parentCode; 
    
    public LocationSaveDTO() {}
    
    public LocationSaveDTO(String code, String name, ELocationType type, String parentCode) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.parentCode = parentCode;
    }
    
    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public ELocationType getType() { return type; }
    public void setType(ELocationType type) { this.type = type; }
    
    public String getParentCode() { return parentCode; }
    public void setParentCode(String parentCode) { this.parentCode = parentCode; }
}

