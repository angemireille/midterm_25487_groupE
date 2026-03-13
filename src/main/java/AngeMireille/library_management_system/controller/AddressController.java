package AngeMireille.library_management_system.controller;

import AngeMireille.library_management_system.dto.AddressDTO;
import AngeMireille.library_management_system.dto.AddressSaveDTO;
import AngeMireille.library_management_system.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @PostMapping("/save")
    public String saveAddress(@RequestBody AddressSaveDTO addressSaveDTO) {
        return addressService.saveAddress(addressSaveDTO);
    }

    @GetMapping("/all")
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public AddressDTO getAddressById(@PathVariable int id) {
        return addressService.getAddressById(id);
    }
}