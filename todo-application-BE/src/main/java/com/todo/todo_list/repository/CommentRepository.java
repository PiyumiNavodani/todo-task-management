package com.todo.todo_list.repository;

import com.todo.todo_list.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author by piyumi_navodani
 */

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
