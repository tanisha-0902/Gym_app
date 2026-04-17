package com.gym.gymapp.service;

import com.gym.gymapp.model.Member; // Updated to Uppercase
import com.gym.gymapp.model.Trainer;
import com.gym.gymapp.repository.MemberRepository;
import com.gym.gymapp.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    // --- MEMBER LOGIC ---

    public Member registerMember(Member member) { // Updated to Uppercase
        member.setStartDate(LocalDate.now());
        member.setEndDate(calculateEndDate(member.getStartDate(), member.getMembershipType()));
        return memberRepository.save(member);
    }

    public Member getMemberById(Long id) { // Updated to Uppercase
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member with ID " + id + " not found"));
    }

    public Member renewMembership(Long id, String newType) { // Updated to Uppercase
        Member member = getMemberById(id);
        member.setMembershipType(newType);
        member.setStartDate(LocalDate.now());
        member.setEndDate(calculateEndDate(member.getStartDate(), newType));
        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() { return memberRepository.findAll(); }

    public void deleteMember(Long id) { memberRepository.deleteById(id); }

    // --- TRAINER LOGIC ---

    public Trainer addTrainer(Trainer trainer) { return trainerRepository.save(trainer); }

    public List<Trainer> getAllTrainers() { return trainerRepository.findAll(); }

    public void deleteTrainer(String id) { trainerRepository.deleteById(id); }

    public boolean loginTrainer(String id, String password) {
        return trainerRepository.findById(id)
                .map(t -> t.getPassword().equals(password))
                .orElse(false);
    }

    public Member assignTrainer(Long memberId, String trainerId) { // Updated to Uppercase
        Member member = getMemberById(memberId);
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        member.setAssignedTrainer(trainer);
        return memberRepository.save(member);
    }

    // --- HELPER ---

    private LocalDate calculateEndDate(LocalDate start, String type) {
        if (type == null) return start.plusMonths(1); // Safety fallback
        return switch (type.toLowerCase()) {
            case "monthly" -> start.plusMonths(1);
            case "quarterly" -> start.plusMonths(3);
            case "yearly" -> start.plusYears(1);
            default -> start.plusMonths(1); // Default to monthly if type is unknown
        };
    }
}