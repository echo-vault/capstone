package com.echovault.capstone.repositories;

import com.echovault.capstone.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
