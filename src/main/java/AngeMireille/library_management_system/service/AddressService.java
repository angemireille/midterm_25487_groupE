package AngeMireille.library_management_system.service;

import AngeMireille.library_management_system.dto.AddressDTO;
import AngeMireille.library_management_system.dto.AddressSaveDTO;
import java.util.List;

public interface AddressService {
    String saveAddress(AddressSaveDTO addressSaveDTO);
    List<AddressDTO> getAllAddresses();
    AddressDTO getAddressById(int id);
}