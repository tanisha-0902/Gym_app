package com.gym.gymapp.repository;

import com.gym.gymapp.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Spring Boot will automatically implement this search logic for you!
    List<Member> findByAssignedTrainer_TrainerId(String trainerId);
}