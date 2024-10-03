package com.example.it22123.repository;

import com.example.it22123.entity.Cooperative;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Hidden
public interface CooperativeRepository extends JpaRepository<Cooperative, Integer> {



    List<Cooperative> findByUserId(Long userId);
}
