package AngeMireille.library_management_system.service.IMPL;

import AngeMireille.library_management_system.dto.AddressDTO;
import AngeMireille.library_management_system.dto.AddressSaveDTO;
import AngeMireille.library_management_system.model.Address;
import AngeMireille.library_management_system.repository.AddressRepo;
import AngeMireille.library_management_system.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceIMPL implements AddressService {

    @Autowired
    private AddressRepo addressRepo;

    @Override
    public String saveAddress(AddressSaveDTO addressSaveDTO) {
        Address address = new Address(
            addressSaveDTO.getStreet(),
            addressSaveDTO.getCity(),
            addressSaveDTO.getProvince(),
            addressSaveDTO.getPostalCode(),
            addressSaveDTO.getCountry()
        );
        addressRepo.save(address);
        return "Address saved successfully";
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        List<Address> addresses = addressRepo.findAll();
        List<AddressDTO> addressDTOs = new ArrayList<>();
        
        for (Address address : addresses) {
            addressDTOs.add(new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getProvince(),
                address.getPostalCode(),
                address.getCountry()
            ));
        }
        return addressDTOs;
    }

    @Override
    public AddressDTO getAddressById(int id) {
        Address address = addressRepo.findById(id).orElse(null);
        if (address != null) {
            return new AddressDTO(
                address.getId(),
                address.getStreet(),
                address.getCity(),
                address.getProvince(),
                address.getPostalCode(),
                address.getCountry()
            );
        }
        return null;
    }
}