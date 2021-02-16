package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Replay class, represents a database entity.
 */
@Data
@Entity
@Table(name = "REPLY")
public class Reply {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String author;

    @Column
    private String message;

    @ManyToOne
    private Question question;
}
