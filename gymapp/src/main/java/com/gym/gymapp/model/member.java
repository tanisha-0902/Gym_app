package com.gym.gymapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "members")
public class member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String membershipType;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "trainer_id") // This is the Foreign Key column in MySQL
    private Trainer assignedTrainer;

    public member() {}

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