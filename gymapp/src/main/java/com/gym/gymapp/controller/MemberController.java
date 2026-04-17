package com.gym.gymapp.controller;

import com.gym.gymapp.model.member;
import com.gym.gymapp.model.Trainer;
import com.gym.gymapp.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Allows your frontend to talk to this backend
@RestController
@RequestMapping("/api/gym")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // --- ADMIN ENDPOINTS ---

    @PostMapping("/members/add")
    public member addMember(@RequestBody member member) {
        return memberService.registerMember(member);
    }

    @GetMapping("/members/all")
    public List<member> listMembers() {
        return memberService.getAllMembers();
    }

    @DeleteMapping("/members/{id}")
    public void deleteMember(@PathVariable Long id) { // Changed to void/ResponseEntity for cleaner API
        memberService.deleteMember(id);
    }

    @PostMapping("/trainers/add")
    public Trainer addTrainer(@RequestBody Trainer trainer) {
        return memberService.addTrainer(trainer);
    }

    // --- TRAINER ENDPOINTS ---

    // Keep this as POST but ensure app.js sends it correctly
    @PostMapping("/trainers/login")
    public String login(@RequestParam String id, @RequestParam String password) {
        return memberService.loginTrainer(id, password) ? "Login Successful" : "Invalid Credentials";
    }

    @PutMapping("/assign/{memberId}/{trainerId}")
    public member assignTrainer(@PathVariable Long memberId, @PathVariable String trainerId) {
        return memberService.assignTrainer(memberId, trainerId);
    }

    // --- CUSTOMER ENDPOINTS ---

    @GetMapping("/members/{id}")
    public member viewProfile(@PathVariable Long id) {
        return memberService.getMemberById(id);
    }

    @PutMapping("/members/renew/{id}")
    public member renew(@PathVariable Long id, @RequestParam String type) {
        return memberService.renewMembership(id, type);
    }
}