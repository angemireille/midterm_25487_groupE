package AngeMireille.library_management_system.service;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import AngeMireille.library_management_system.dto.LocationDTO;
import AngeMireille.library_management_system.dto.LocationSaveDTO;
import AngeMireille.library_management_system.model.ELocationType;
import AngeMireille.library_management_system.model.Location;
import AngeMireille.library_management_system.repository.LocationRepository;

@Service
public class LocationService {

    @Autowired
    private LocationRepository locationRepo;

    public String saveLocation(LocationSaveDTO locationSaveDTO) {
        Location location = new Location(locationSaveDTO.getCode(), locationSaveDTO.getName(), locationSaveDTO.getType());
        
        if (locationSaveDTO.getParentCode() != null) {
            Location parent = locationRepo.findByCode(locationSaveDTO.getParentCode());
            if (parent == null) {
                return "Parent location not found.";
            }
            location.setParent(parent);
        }
        
        if (!location.getType().equals(ELocationType.PROVINCE) && locationSaveDTO.getParentCode() == null) {
            return "Parent location is required for non-province locations.";
        }
        
        Boolean exists = locationRepo.existsByCode(location.getCode());
        if (exists) {
            return "Location with this code already exists.";
        }
        
        locationRepo.save(location);
        return "Location saved successfully.";
    }
    
    public Location getLocationByCode(String code) {
        return locationRepo.findByCode(code);
    }
    
    public List<LocationDTO> getAllProvinces() {
        return locationRepo.findByType(ELocationType.PROVINCE)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<LocationDTO> getDistrictsByProvince(String provinceCode) {
        Location province = locationRepo.findByCode(provinceCode);
        if (province == null) return List.of();
        return locationRepo.findByTypeAndParentId(ELocationType.DISTRICT, province.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<LocationDTO> getSectorsByDistrict(String districtCode) {
        Location district = locationRepo.findByCode(districtCode);
        if (district == null) return List.of();
        return locationRepo.findByTypeAndParentId(ELocationType.SECTOR, district.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<LocationDTO> getCellsBySector(String sectorCode) {
        Location sector = locationRepo.findByCode(sectorCode);
        if (sector == null) return List.of();
        return locationRepo.findByTypeAndParentId(ELocationType.CELL, sector.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<LocationDTO> getVillagesByCell(String cellCode) {
        Location cell = locationRepo.findByCode(cellCode);
        if (cell == null) return List.of();
        return locationRepo.findByTypeAndParentId(ELocationType.VILLAGE, cell.getId())
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<LocationDTO> getAllVillagesInProvince(UUID provinceId) {
        return locationRepo.findAllVillagesInProvince(provinceId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private LocationDTO convertToDTO(Location location) {
        return new LocationDTO(
                location.getId(),
                location.getCode(),
                location.getName(),
                location.getType(),
                location.getParent() != null ? location.getParent().getId() : null,
                location.getParent() != null ? location.getParent().getName() : null
        );
    }
}