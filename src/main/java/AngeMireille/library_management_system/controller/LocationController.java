package AngeMireille.library_management_system.controller;

import AngeMireille.library_management_system.dto.LocationDTO;
import AngeMireille.library_management_system.dto.LocationSaveDTO;
import AngeMireille.library_management_system.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

  
    @PostMapping("/save")
    public ResponseEntity<String> saveLocation(@RequestBody LocationSaveDTO locationSaveDTO) {
        String response = locationService.saveLocation(locationSaveDTO);
        if (response.equals("Location saved successfully.")) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/provinces")
    public List<LocationDTO> getAllProvinces() {
        return locationService.getAllProvinces();
    }

    @GetMapping("/districts/{provinceCode}")
    public List<LocationDTO> getDistrictsByProvince(@PathVariable String provinceCode) {
        return locationService.getDistrictsByProvince(provinceCode);
    }

   
    @GetMapping("/sectors/{districtCode}")
    public List<LocationDTO> getSectorsByDistrict(@PathVariable String districtCode) {
        return locationService.getSectorsByDistrict(districtCode);
    }

   
    @GetMapping("/cells/{sectorCode}")
    public List<LocationDTO> getCellsBySector(@PathVariable String sectorCode) {
        return locationService.getCellsBySector(sectorCode);
    }

   
    @GetMapping("/villages/{cellCode}")
    public List<LocationDTO> getVillagesByCell(@PathVariable String cellCode) {
        return locationService.getVillagesByCell(cellCode);
    }

   
    @GetMapping("/province/{provinceId}/villages")
    public List<LocationDTO> getAllVillagesInProvince(@PathVariable UUID provinceId) {
        return locationService.getAllVillagesInProvince(provinceId);
    }
}