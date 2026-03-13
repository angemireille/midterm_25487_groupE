package AngeMireille.library_management_system.repository;

import AngeMireille.library_management_system.model.ELocationType;
import AngeMireille.library_management_system.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    
    boolean existsByCode(String code);
    
    List<Location> findByType(ELocationType type);
    
    @Query("SELECT l FROM Location l WHERE l.type = :type AND l.parent.id = :parentId")
    List<Location> findByTypeAndParentId(@Param("type") ELocationType type, @Param("parentId") UUID parentId);
    
    @Query("SELECT l FROM Location l WHERE l.code = :code")
    Location findByCode(@Param("code") String code);
    
    
    @Query("SELECT l FROM Location l WHERE l.type = 'VILLAGE' AND l.parent.parent.parent.parent.id = :provinceId")
    List<Location> findAllVillagesInProvince(@Param("provinceId") UUID provinceId);
}