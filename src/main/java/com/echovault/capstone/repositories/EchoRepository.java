package com.echovault.capstone.repositories;

import com.echovault.capstone.models.Echo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EchoRepository extends JpaRepository<Echo, Long> {

    List<Echo> findAll();

}
