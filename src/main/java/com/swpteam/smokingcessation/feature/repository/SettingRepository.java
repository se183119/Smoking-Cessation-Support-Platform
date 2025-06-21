package com.swpteam.smokingcessation.feature.repository;

import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.domain.enums.MotivationFrequency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SettingRepository extends JpaRepository<Setting, String> {

//    List<Setting> findByReportDeadlineAndIsDeletedFalse(LocalTime deadlineTime);
@Query(
        value = "SELECT * FROM setting WHERE is_deleted = 0 AND report_deadline BETWEEN CAST(:from AS TIME) AND CAST(:to AS TIME)",
        nativeQuery = true)
List<Setting> findAllByReportDeadlineBetween(@Param("from") String from, @Param("to") String to);


    List<Setting> findByMotivationFrequency(MotivationFrequency motivationFrequency);

    Optional<Setting> findByIdAndIsDeletedFalse(String id);

    Optional<Setting> findByAccountIdAndIsDeletedFalse(String accountId);

    Page<Setting> findAllByIsDeletedFalse(Pageable pageable);
}
