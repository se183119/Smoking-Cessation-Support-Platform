package com.swpteam.smokingcessation.feature.service.impl.profile;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.HealthMapper;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.dto.health.HealthCreateRequest;
import com.swpteam.smokingcessation.domain.dto.health.HealthResponse;
import com.swpteam.smokingcessation.domain.dto.health.HealthUpdateRequest;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Health;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.feature.repository.HealthRepository;
import com.swpteam.smokingcessation.feature.service.interfaces.profile.HealthService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class HealthServiceImpl implements HealthService {
    HealthRepository healthRepository;
    AccountRepository accountRepository;
    HealthMapper healthMapper;

    @Override
    public Page<HealthResponse> getHealthPage(PageableRequest request) {
        if (!ValidationUtil.isFieldExist(Health.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Health> healths = healthRepository.findAllByIsDeletedFalse(pageable);

        return healths.map(healthMapper::toResponse);
    }

    @Override
    public HealthResponse getHealthById(String id) {
        return healthMapper.toResponse(
                healthRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND)));
    }

    @Override
    public Page<HealthResponse> getHealthPageByAccountId(String accountId, PageableRequest request) {
        if (!ValidationUtil.isFieldExist(Health.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Health> healths = healthRepository.findByAccountIdAndIsDeletedFalse(accountId, pageable);

        return healths.map(healthMapper::toResponse);
    }

    @Override
    @Transactional
    public HealthResponse createHealth(HealthCreateRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Health health = healthMapper.toEntity(request);

        health.setAccount(account);

        return healthMapper.toResponse(healthRepository.save(health));
    }

    @Override
    @Transactional
    public HealthResponse updateHealth(String id, HealthUpdateRequest request) {
        Health health = healthRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND));

        healthMapper.updateHealth(health, request);

        return healthMapper.toResponse(healthRepository.save(health));
    }

    @Override
    @Transactional
    public void softDeleteHealthById(String id) {
        Health health = healthRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND));

        health.setDeleted(true);

        healthRepository.save(health);
    }
}