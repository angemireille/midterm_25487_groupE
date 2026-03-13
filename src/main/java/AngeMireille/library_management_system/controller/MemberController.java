package AngeMireille.library_management_system.controller;

import AngeMireille.library_management_system.dto.MemberDTO;
import AngeMireille.library_management_system.dto.LoginResponseDTO;
import AngeMireille.library_management_system.dto.TwoFARequestDTO;
import AngeMireille.library_management_system.dto.MemberSaveDTO;
import AngeMireille.library_management_system.dto.MemberUpdateDTO;
import AngeMireille.library_management_system.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.*;

@RestController
@RequestMapping("/api/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

  
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        String email = body.get("email");
        String password = body.get("password");

    
        Optional<MemberDTO> existing = memberService.findByEmail(email);
        if (existing.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Map.of(
                    "success", false,
                    "message", "Email already registered."
                ));
        }

        // Create and save new member
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setName(name);
        memberSaveDTO.setEmail(email);
        memberSaveDTO.setPassword(password);

        try {
            String result = memberService.addMember(memberSaveDTO);
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Signup successful.",
                "memberName", result
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of(
                    "success", false,
                    "message", "Signup failed: " + e.getMessage()
                ));
        }
    }

    // --- LOGIN with 2FA ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String name = loginData.get("name");
        String password = loginData.get("password");
        Optional<MemberDTO> member = memberService.login(name, password);
        if (member.isPresent()) {
            memberService.send2faCode(member.get().getEmail());
            return ResponseEntity.ok(Map.of(
                "require2fa", true,
                "email", member.get().getEmail()
            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("message", "Invalid credentials."));
        }
    }

    // --- 2FA Verification ---
    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify2faAndLogin(@RequestBody TwoFARequestDTO request) {
        boolean verified = memberService.verify2fa(request.getEmail(), request.getCode());
        if (!verified) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid 2FA code");
        }

        Optional<MemberDTO> memberOpt = memberService.findByEmail(request.getEmail());
        if (memberOpt.isPresent()) {
            MemberDTO member = memberOpt.get();
            LoginResponseDTO response = new LoginResponseDTO(
                member.getMemberid(),
                member.getName(),
                member.getEmail(),
                member.getRole()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }

    // --- CRUD ---
    @PostMapping("/add")
    public String addMember(@RequestBody MemberSaveDTO memberSaveDTO) {
        return memberService.addMember(memberSaveDTO);
    }

    @GetMapping("/getAllMember")
    public List<MemberDTO> getAllMember() {
        return memberService.getAllMember();
    }

    @PutMapping("/update")
    public String updateMember(@RequestBody MemberUpdateDTO memberUpdateDTO) {
        return memberService.updateMember(memberUpdateDTO);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMember(@PathVariable int id) {
        return memberService.deleteMember(id);
    }

   
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        memberService.initiatePasswordReset(email);
        return ResponseEntity.ok(Collections.singletonMap("message", "If this email exists, a reset link has been sent."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String newPassword = body.get("newPassword");
        boolean success = memberService.resetPassword(token, newPassword);
        if (success) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Password reset successful."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("message", "Invalid or expired token."));
        }
    }

    @GetMapping("/verify-reset-token")
    public ResponseEntity<?> verifyResetToken(@RequestParam String token) {
        boolean valid = memberService.verifyResetToken(token);
        if (valid) {
            return ResponseEntity.ok(Collections.singletonMap("valid", true));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap("valid", false));
        }
    }
    @GetMapping("/by-province/{province}")
     public List<MemberDTO> getMembersByProvince(@PathVariable String province) {
    return memberService.getMembersByProvince(province);
}

@GetMapping("/paginated")
public Page<MemberDTO> getMembersPaginated(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
    
    Sort sort = direction.equalsIgnoreCase("asc") 
        ? Sort.by(sortBy).ascending() 
        : Sort.by(sortBy).descending();
    
    Pageable pageable = PageRequest.of(page, size, sort);
    return memberService.getAllMembersPaginated(pageable);
}

@GetMapping("/sorted")
public List<MemberDTO> getMembersSorted(
        @RequestParam(defaultValue = "name") String sortBy,
        @RequestParam(defaultValue = "asc") String direction) {
    
    Sort sort = direction.equalsIgnoreCase("asc") 
        ? Sort.by(sortBy).ascending() 
        : Sort.by(sortBy).descending();
    
    return memberService.getAllMembersSorted(sort);
}
@GetMapping("/by-province/id/{provinceId}")
public List<MemberDTO> getMembersByProvinceId(@PathVariable UUID provinceId) {
    return memberService.getMembersByProvinceId(provinceId);
}

@GetMapping("/by-province/code/{provinceCode}")
public List<MemberDTO> getMembersByProvinceCode(@PathVariable String provinceCode) {
    return memberService.getMembersByProvinceCode(provinceCode);
}


@GetMapping("/by-province/name/{provinceName}")
public List<MemberDTO> getMembersByProvinceName(@PathVariable String provinceName) {
    return memberService.getMembersByProvinceName(provinceName);
}

}