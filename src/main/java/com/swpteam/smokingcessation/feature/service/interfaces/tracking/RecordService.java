package com.swpteam.smokingcessation.feature.service.interfaces.tracking;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordCreateRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordResponse;
import com.swpteam.smokingcessation.domain.dto.record.RecordUpdateRequest;
import org.springframework.data.domain.Page;

public interface RecordService {

    Page<RecordResponse> getRecordPage(PageableRequest request);

    RecordResponse getRecordById(String id);

    Page<RecordResponse> getRecordPageByAccountId(String accountId, PageableRequest request);

    RecordResponse createRecord(RecordCreateRequest request);

    RecordResponse updateRecord(String id, RecordUpdateRequest request);

    void softDeleteRecordById(String id);
}
