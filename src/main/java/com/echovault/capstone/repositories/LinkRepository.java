package com.echovault.capstone.repositories;

import com.echovault.capstone.models.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {
}
