package com.example.demo.repository;

import com.example.demo.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Reply repository interface, extends Spring Data interface to provide CRUD methods on REPLIES table.
 */
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
