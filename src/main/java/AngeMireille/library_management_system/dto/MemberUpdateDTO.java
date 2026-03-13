package AngeMireille.library_management_system.dto;

public class MemberUpdateDTO {
    private int memberid;
    private String name;
    private String email;

    public MemberUpdateDTO() {}

    public MemberUpdateDTO(int memberid, String name, String email) {
        this.memberid = memberid;
        this.name = name;
        this.email = email;
    }

    // Getters and setters
    public int getMemberid() { return memberid; }
    public void setMemberid(int memberid) { this.memberid = memberid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}