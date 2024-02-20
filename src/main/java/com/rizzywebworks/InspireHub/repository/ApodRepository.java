package com.rizzywebworks.InspireHub.repository;

import com.rizzywebworks.InspireHub.entity.ApodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ApodRepository extends JpaRepository<ApodEntity, Long> {

    ApodEntity findByDate(LocalDate date);

    boolean existsByDate(LocalDate date);
}
