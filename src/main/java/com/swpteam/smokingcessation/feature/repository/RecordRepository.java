package com.swpteam.smokingcessation.feature.repository;

import com.swpteam.smokingcessation.domain.entity.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecordRepository extends JpaRepository<com.swpteam.smokingcessation.domain.entity.Record, String> {

    Optional<com.swpteam.smokingcessation.domain.entity.Record> findByIdAndIsDeletedFalse(String id);

    Page<com.swpteam.smokingcessation.domain.entity.Record> findByAccountIdAndIsDeletedFalse(String accountId, Pageable pageable);

    Page<Record> findAllByIsDeletedFalse(Pageable pageable);
}