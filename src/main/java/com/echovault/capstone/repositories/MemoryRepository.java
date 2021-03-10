package com.echovault.capstone.repositories;

import com.echovault.capstone.models.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Long> {
}
