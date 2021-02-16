package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Question repository interface, extends Spring Data interface to provide CRUD methods on QUESTIONS table.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
