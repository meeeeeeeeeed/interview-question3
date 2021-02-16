package com.example.demo.entity;

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
@Entity
@Table(name = "QUESTION")
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String message;

    @OneToMany
    private List<Reply> replies;
}
