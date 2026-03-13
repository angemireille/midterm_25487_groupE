package AngeMireille.library_management_system.service.IMPL;

import AngeMireille.library_management_system.dto.MemberDTO;
import AngeMireille.library_management_system.dto.MemberSaveDTO;
import AngeMireille.library_management_system.dto.MemberUpdateDTO;
import static AngeMireille.library_management_system.model.ELocationType.*;
import AngeMireille.library_management_system.model.Location;
import AngeMireille.library_management_system.model.Member;
import AngeMireille.library_management_system.repository.LocationRepository;
import AngeMireille.library_management_system.repository.MemberRepo;
import AngeMireille.library_management_system.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MemberServiceIMPL implements MemberService {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private LocationRepository locationRepo;  

    private final Map<String, String> twoFaCodes = new HashMap<>();
    private final Map<String, LocalDateTime> twoFaExpiry = new HashMap<>();

    @Override
    public String addMember(MemberSaveDTO memberSaveDTO) {
       
        if (memberRepo.existsByEmail(memberSaveDTO.getEmail())) {
            throw new RuntimeException("Email already exists!");
        }
        
        String defaultRole = "User";
        
        Member member = new Member(
            memberSaveDTO.getName(),
            memberSaveDTO.getEmail(),
            memberSaveDTO.getPassword(),
            defaultRole
        );
        
       
        if (memberSaveDTO.getVillageCode() != null && !memberSaveDTO.getVillageCode().isEmpty()) {
            Location village = locationRepo.findByCode(memberSaveDTO.getVillageCode());
            if (village == null) {
                throw new RuntimeException("Village with code " + memberSaveDTO.getVillageCode() + " not found.");
            }
            if (!village.getType().equals(VILLAGE)) {
                throw new RuntimeException("Location with code " + memberSaveDTO.getVillageCode() + " is not a VILLAGE.");
            }
            member.setVillage(village);
        }
        
        memberRepo.save(member);
        return member.getName();
    }

    @Override
    public List<MemberDTO> getAllMember() {
        List<Member> getMembers = memberRepo.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (Member member : getMembers) {
            MemberDTO memberDTO = new MemberDTO(
                member.getMemberid(),
                member.getName(),
                member.getEmail(),
                member.getRole()
            );
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    @Override
    public String updateMember(MemberUpdateDTO memberUpdateDTO) {
        return memberRepo.findById(memberUpdateDTO.getMemberid())
            .map(member -> {
                member.setName(memberUpdateDTO.getName());
                member.setEmail(memberUpdateDTO.getEmail());
                memberRepo.save(member);
                return member.getName();
            })
            .orElse(null);
    }

    @Override
    public String deleteMember(int id) {
        if (memberRepo.existsById(id)) {
            memberRepo.deleteById(id);
            return "Deleted";
        } else {
            return "ID Not Found!";
        }
    }

    @Override
    public Optional<MemberDTO> findByEmail(String email) {
        Optional<Member> member = memberRepo.findByEmail(email);
        return member.map(m -> new MemberDTO(m.getMemberid(), m.getName(), m.getEmail(), m.getRole()));
    }

    @Override
    public Optional<MemberDTO> login(String name, String password) {
        Optional<Member> member = memberRepo.findByNameAndPassword(name, password);
        return member.map(m -> new MemberDTO(m.getMemberid(), m.getName(), m.getEmail(), m.getRole()));
    }

    @Override
    public void send2faCode(String email) {
        String code = String.valueOf(new Random().nextInt(900000) + 100000);
        twoFaCodes.put(email, code);
        twoFaExpiry.put(email, LocalDateTime.now().plusMinutes(5));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your 2FA Code");
        message.setText("Your 2FA code is: " + code + ". It expires in 5 minutes.");
        mailSender.send(message);
        System.out.println("2FA code for " + email + ": " + code);
    }

    @Override
    public boolean verify2fa(String email, String code) {
        String storedCode = twoFaCodes.get(email);
        LocalDateTime expiry = twoFaExpiry.get(email);
        if (storedCode != null && expiry != null && expiry.isAfter(LocalDateTime.now())) {
            if (storedCode.equals(code)) {
                twoFaCodes.remove(email);
                twoFaExpiry.remove(email);
                return true;
            }
        }
        return false;
    }

    @Override
    public void initiatePasswordReset(String email) {
        Optional<Member> memberOpt = memberRepo.findByEmail(email);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            String token = UUID.randomUUID().toString();
            member.setResetToken(token);
            member.setResetTokenExpiry(LocalDateTime.now().plusHours(1));
            memberRepo.save(member);

            String resetLink = "http://localhost:3000/reset-password?token=" + token;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset Request");
            message.setText("Click the link to reset your password: " + resetLink);
            mailSender.send(message);
        }
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<Member> memberOpt = memberRepo.findByResetToken(token);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            if (member.getResetTokenExpiry() != null && member.getResetTokenExpiry().isAfter(LocalDateTime.now())) {
                member.setPassword(newPassword);
                member.setResetToken(null);
                member.setResetTokenExpiry(null);
                memberRepo.save(member);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean verifyResetToken(String token) {
        Optional<Member> memberOpt = memberRepo.findByResetToken(token);
        if (memberOpt.isPresent()) {
            Member member = memberOpt.get();
            return member.getResetTokenExpiry() != null &&
                   member.getResetTokenExpiry().isAfter(LocalDateTime.now());
        }
        return false;
    }

    @Override
    public List<MemberDTO> getMembersByProvinceId(UUID provinceId) {
        List<Member> members = memberRepo.findByProvinceId(provinceId);
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            memberDTOs.add(new MemberDTO(
                member.getMemberid(),
                member.getName(),
                member.getEmail(),
                member.getRole()
            ));
        }
        return memberDTOs;
    }

    @Override
    public List<MemberDTO> getMembersByProvinceCode(String provinceCode) {
        List<Member> members = memberRepo.findByProvinceCode(provinceCode);
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            memberDTOs.add(new MemberDTO(
                member.getMemberid(),
                member.getName(),
                member.getEmail(),
                member.getRole()
            ));
        }
        return memberDTOs;
    }

    @Override
    public List<MemberDTO> getMembersByProvince(String province) {
        
        return getMembersByProvinceCode(province);
    }

    @Override
    public List<MemberDTO> getMembersByProvinceName(String provinceName) {
        List<Member> members = memberRepo.findByProvinceName(provinceName);
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            memberDTOs.add(new MemberDTO(
                member.getMemberid(),
                member.getName(),
                member.getEmail(),
                member.getRole()
            ));
        }
        return memberDTOs;
    }

    @Override
    public Page<MemberDTO> getAllMembersPaginated(Pageable pageable) {
        Page<Member> memberPage = memberRepo.findAll(pageable);
        return memberPage.map(member -> new MemberDTO(
            member.getMemberid(),
            member.getName(),
            member.getEmail(),
            member.getRole()
        ));
    }

    @Override
    public List<MemberDTO> getAllMembersSorted(Sort sort) {
        List<Member> members = memberRepo.findAll(sort);
        List<MemberDTO> memberDTOs = new ArrayList<>();
        for (Member member : members) {
            memberDTOs.add(new MemberDTO(
                member.getMemberid(),
                member.getName(),
                member.getEmail(),
                member.getRole()
            ));
        }
        return memberDTOs;
    }
    
}