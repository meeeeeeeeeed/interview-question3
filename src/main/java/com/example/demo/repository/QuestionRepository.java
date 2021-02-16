package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Question repository interface, extends Spring Data interface to provide CRUD methods for QUESTION table.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
