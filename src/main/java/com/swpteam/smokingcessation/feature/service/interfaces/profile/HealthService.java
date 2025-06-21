package com.swpteam.smokingcessation.feature.service.interfaces.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.health.HealthCreateRequest;
import com.swpteam.smokingcessation.domain.dto.health.HealthResponse;
import com.swpteam.smokingcessation.domain.dto.health.HealthUpdateRequest;
import org.springframework.data.domain.Page;

public interface HealthService {

    public Page<HealthResponse> getHealthPage(PageableRequest request);

    public HealthResponse getHealthById(String id);

    public Page<HealthResponse> getHealthPageByAccountId(String accountId, PageableRequest request);

    public HealthResponse createHealth(HealthCreateRequest request);

    public HealthResponse updateHealth(String id, HealthUpdateRequest request);

    public void softDeleteHealthById(String id);
}
