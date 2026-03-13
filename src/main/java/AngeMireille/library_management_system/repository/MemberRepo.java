package AngeMireille.library_management_system.repository;

import AngeMireille.library_management_system.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepo extends JpaRepository<Member, Integer> {
    
    Optional<Member> findByEmail(String email);
    
    Optional<Member> findByResetToken(String resetToken);
    
    Optional<Member> findByNameAndPassword(String name, String password);
    
    boolean existsByEmail(String email);
    
    boolean existsByName(String name);
    
    
    List<Member> findAll(Sort sort);
   
    Page<Member> findAll(Pageable pageable);
    
   
    @Query("SELECT m FROM Member m WHERE m.village.parent.parent.parent.parent.id = :provinceId")
    List<Member> findByProvinceId(@Param("provinceId") UUID provinceId);
    
    @Query("SELECT m FROM Member m WHERE m.village.parent.parent.parent.parent.code = :provinceCode")
    List<Member> findByProvinceCode(@Param("provinceCode") String provinceCode);
   
    @Query("SELECT m FROM Member m WHERE m.village.parent.parent.parent.parent.name = :provinceName")
    List<Member> findByProvinceName(@Param("provinceName") String provinceName);
    
    List<Member> findByVillageId(UUID villageId);
    Page<Member> findByVillageId(UUID villageId, Pageable pageable);
    
   
    @Query("SELECT m FROM Member m WHERE m.village.code = :villageCode")
    List<Member> findByVillageCode(@Param("villageCode") String villageCode);
}