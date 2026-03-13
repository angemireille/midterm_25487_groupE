package AngeMireille.library_management_system.service;

import AngeMireille.library_management_system.dto.MemberDTO;
import AngeMireille.library_management_system.dto.MemberSaveDTO;
import AngeMireille.library_management_system.dto.MemberUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberService {
    String addMember(MemberSaveDTO memberSaveDTO);
    List<MemberDTO> getAllMember();
    String updateMember(MemberUpdateDTO memberUpdateDTO);
    String deleteMember(int id);
    Optional<MemberDTO> login(String name, String password);
    Optional<MemberDTO> findByEmail(String email);
    List<MemberDTO> getMembersByProvince(String province);
    Page<MemberDTO> getAllMembersPaginated(Pageable pageable);
    List<MemberDTO> getAllMembersSorted(Sort sort);
    List<MemberDTO> getMembersByProvinceId(UUID provinceId);
    List<MemberDTO> getMembersByProvinceCode(String provinceCode);
    List<MemberDTO> getMembersByProvinceName(String provinceName);
    // 2FA
    void send2faCode(String email);
    boolean verify2fa(String email, String code);

    // Password reset
    void initiatePasswordReset(String email);
    boolean resetPassword(String token, String newPassword);
    boolean verifyResetToken(String token);

}
