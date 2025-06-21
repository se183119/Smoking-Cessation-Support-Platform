package com.swpteam.smokingcessation.feature.repository;

import com.swpteam.smokingcessation.domain.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndIsDeletedFalse(String email);

    Optional<Account> findById(String s);

    Optional<Account> findByIdAndIsDeletedFalse(String id);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Page<Account> findAllByIsDeletedFalse(Pageable pageable);

    Page<Account> findAll(Pageable pageable);
}
