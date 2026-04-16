package com.gym.gymapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "trainers")
public class Trainer {

    @Id
    private String trainerId; // Unique ID (e.g., "TRAIN_01")
    private String password;
    private String specialization;

    @OneToMany(mappedBy = "assignedTrainer")
    @JsonIgnore // Prevents infinite loops during JSON conversion
    private List<member> members;

    public Trainer() {}

    public String getTrainerId() { return trainerId; }
    public void setTrainerId(String trainerId) { this.trainerId = trainerId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public List<member> getMembers() { return members; }
    public void setMembers(List<member> members) { this.members = members; }
}