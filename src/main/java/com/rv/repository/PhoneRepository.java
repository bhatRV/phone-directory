package com.rv.repository;


import com.rv.entities.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneNumber, Long> {

    Optional<PhoneNumber> findByNumber(@Param("number") String number);

}