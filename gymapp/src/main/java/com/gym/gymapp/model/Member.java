package com.gym.gymapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "members")
public class Member { // Renamed to Uppercase 'Member'

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String membershipType;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer assignedTrainer;

    // Default Constructor
    public Member() {}

    // AUTOMATIC DATE LOGIC: This sets the dates when you save a new member
    @PrePersist
    protected void onCreate() {
        this.startDate = LocalDate.now();
        // Default to 1 month membership if not calculated elsewhere
        if (this.endDate == null) {
            this.endDate = this.startDate.plusMonths(1);
        }
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Trainer getAssignedTrainer() { return assignedTrainer; }
    public void setAssignedTrainer(Trainer trainer) { this.assignedTrainer = trainer; }
}