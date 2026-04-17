package com.gym.gymapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    private String trainerId;
    private String password;
    private String specialization;

    // UPDATED: Changed 'member' to 'Member' to match the renamed class
    @OneToMany(mappedBy = "assignedTrainer")
    @JsonIgnore
    private List<Member> members;

    public Trainer() {}

    public String getTrainerId() { return trainerId; }
    public void setTrainerId(String trainerId) { this.trainerId = trainerId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    // UPDATED: Changed 'member' to 'Member'
    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }
}