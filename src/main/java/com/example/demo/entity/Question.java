package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Question class, represents a database entity.
 */
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "QUESTIONS")
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String message;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "question")
    private List<Reply> replies;
}
