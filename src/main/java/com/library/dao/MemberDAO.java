package com.library.dao;

import com.library.entity.Member;
import com.library.util.FileUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private static final String MEMBERS_FILE = "members.txt";
    
    public boolean addMember(Member member) {
        try {
            member.setMemberId(getNextId());
            String line = memberToString(member);
            FileUtil.appendToFile(MEMBERS_FILE, line);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Member> getAllMembers() {
        List<Member> members = new ArrayList<>();
        List<String> lines = FileUtil.readFile(MEMBERS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                members.add(stringToMember(line));
            }
        }
        return members;
    }
    
    public Member getMemberById(int memberId) {
        List<String> lines = FileUtil.readFile(MEMBERS_FILE);
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Member member = stringToMember(line);
                if (member.getMemberId() == memberId) {
                    return member;
                }
            }
        }
        return null;
    }
    
    public boolean updateMember(Member member) {
        List<String> lines = FileUtil.readFile(MEMBERS_FILE);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Member existingMember = stringToMember(line);
                if (existingMember.getMemberId() == member.getMemberId()) {
                    updatedLines.add(memberToString(member));
                    found = true;
                } else {
                    updatedLines.add(line);
                }
            }
        }
        
        if (found) {
            FileUtil.writeFile(MEMBERS_FILE, updatedLines);
        }
        return found;
    }
    
    public boolean deleteMember(int memberId) {
        List<String> lines = FileUtil.readFile(MEMBERS_FILE);
        List<String> updatedLines = new ArrayList<>();
        boolean found = false;
        
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                Member member = stringToMember(line);
                if (member.getMemberId() != memberId) {
                    updatedLines.add(line);
                } else {
                    found = true;
                }
            }
        }
        
        if (found) {
            FileUtil.writeFile(MEMBERS_FILE, updatedLines);
        }
        return found;
    }
    
    private String memberToString(Member member) {
        return member.getMemberId() + "|" + member.getMembershipNumber() + "|" + member.getFirstName() + "|" +
               member.getLastName() + "|" + member.getEmail() + "|" + member.getPhone() + "|" +
               member.getAddress() + "|" + member.getJoinDate() + "|" + member.getExpiryDate() + "|" +
               member.getMembershipType() + "|" + member.getStatus() + "|" + member.getOutstandingFines();
    }
    
    private Member stringToMember(String line) {
        String[] parts = line.split("\\|");
        Member member = new Member();
        member.setMemberId(Integer.parseInt(parts[0]));
        member.setMembershipNumber(parts[1]);
        member.setFirstName(parts[2]);
        member.setLastName(parts[3]);
        member.setEmail(parts[4]);
        member.setPhone(parts[5]);
        member.setAddress(parts[6]);
        member.setJoinDate(LocalDate.parse(parts[7]));
        member.setExpiryDate(LocalDate.parse(parts[8]));
        member.setMembershipType(parts[9]);
        member.setStatus(parts[10]);
        member.setOutstandingFines(Double.parseDouble(parts[11]));
        return member;
    }
    
    private int getNextId() {
        List<String> lines = FileUtil.readFile(MEMBERS_FILE);
        int maxId = 0;
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] parts = line.split("\\|");
                int id = Integer.parseInt(parts[0]);
                if (id > maxId) maxId = id;
            }
        }
        return maxId + 1;
    }
}