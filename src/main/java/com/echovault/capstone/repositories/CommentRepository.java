package com.echovault.capstone.repositories;

import com.echovault.capstone.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
