package AngeMireille.library_management_system.repository;

import AngeMireille.library_management_system.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AddressRepo extends JpaRepository<Address, Integer> {
    List<Address> findByProvince(String province);
} 
    

