package AngeMireille.library_management_system.dto;

public class MemberDTO {
    private int memberid;
    private String name;
    private String email;
    private String role;

    public MemberDTO() {}

    public MemberDTO(int memberid, String name, String email, String role) {
        this.memberid = memberid;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}