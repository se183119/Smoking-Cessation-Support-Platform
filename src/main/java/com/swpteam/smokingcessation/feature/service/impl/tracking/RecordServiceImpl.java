package com.swpteam.smokingcessation.feature.service.impl.tracking;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.RecordMapper;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.dto.record.RecordCreateRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordResponse;
import com.swpteam.smokingcessation.domain.dto.record.RecordUpdateRequest;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Record;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.feature.repository.RecordRepository;
import com.swpteam.smokingcessation.feature.service.interfaces.tracking.RecordService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecordServiceImpl implements RecordService {

    RecordRepository recordRepository;
    AccountRepository accountRepository;
    RecordMapper recordMapper;

    @Override
    public Page<RecordResponse> getRecordPage(PageableRequest request) {
        if (!ValidationUtil.isFieldExist(com.swpteam.smokingcessation.domain.entity.Record.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        Pageable pageable = PageableRequest.getPageable(request);
        Page<com.swpteam.smokingcessation.domain.entity.Record> records = recordRepository.findAllByIsDeletedFalse(pageable);

        return records.map(recordMapper::toResponse);
    }

    @Override
    public RecordResponse getRecordById(String id) {
        return recordMapper.toResponse(
                recordRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND)));
    }

    @Override
    public Page<RecordResponse> getRecordPageByAccountId(String accountId, PageableRequest request) {
        if (!ValidationUtil.isFieldExist(com.swpteam.smokingcessation.domain.entity.Record.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Record> records = recordRepository.findByAccountIdAndIsDeletedFalse(accountId, pageable);

        return records.map(recordMapper::toResponse);
    }

    @Override
    @Transactional
    public RecordResponse createRecord(RecordCreateRequest request) {
        Account account = accountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        com.swpteam.smokingcessation.domain.entity.Record record = recordMapper.toEntity(request);

        record.setAccount(account);

        return recordMapper.toResponse(recordRepository.save(record));
    }

    @Override
    @Transactional
    public RecordResponse updateRecord(String id, RecordUpdateRequest request) {
        com.swpteam.smokingcessation.domain.entity.Record record = recordRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND));

        recordMapper.update(record, request);

        return recordMapper.toResponse(recordRepository.save(record));
    }

    @Override
    @Transactional
    public void softDeleteRecordById(String id) {
        com.swpteam.smokingcessation.domain.entity.Record record = recordRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.HEALTH_RECORD_NOT_FOUND));

        record.setDeleted(true);

        recordRepository.save(record);
    }
}