package com.swpteam.smokingcessation.feature.repository;

import com.swpteam.smokingcessation.domain.entity.Health;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthRepository extends JpaRepository<Health, String> {

    Optional<Health> findByIdAndIsDeletedFalse(String id);

    Page<Health> findByAccountIdAndIsDeletedFalse(String accountId, Pageable pageable);

    Page<Health> findAllByIsDeletedFalse(Pageable pageable);
}
