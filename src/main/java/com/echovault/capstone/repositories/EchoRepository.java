package com.echovault.capstone.repositories;

import com.echovault.capstone.models.Echo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EchoRepository extends JpaRepository<Echo, Long> {

}
