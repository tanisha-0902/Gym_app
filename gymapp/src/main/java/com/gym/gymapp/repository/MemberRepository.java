package com.gym.gymapp.repository;

import com.gym.gymapp.model.member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<member, Long> {

    // Spring Boot will automatically implement this search logic for you!
    List<member> findByAssignedTrainer_TrainerId(String trainerId);
}