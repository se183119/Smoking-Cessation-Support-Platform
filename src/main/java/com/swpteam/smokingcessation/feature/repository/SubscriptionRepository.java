package com.swpteam.smokingcessation.feature.repository;

import com.swpteam.smokingcessation.domain.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String> {

    Optional<Subscription> findByIdAndIsDeletedFalse(String id);

    Page<Subscription> findByAccountIdAndIsDeletedFalse(String accountId, Pageable pageable);

    Page<Subscription> findAllByIsDeletedFalse(Pageable pageable);
}
