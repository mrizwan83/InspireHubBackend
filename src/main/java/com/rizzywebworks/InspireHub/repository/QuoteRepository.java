package com.rizzywebworks.InspireHub.repository;

import com.rizzywebworks.InspireHub.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {
    @Query(value = "SELECT * FROM quote_entity ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<QuoteEntity> findRandomQuote();


    void deleteById(Long id);


    boolean existsByDate(String date);
}
