package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.health.HealthCreateRequest;
import com.swpteam.smokingcessation.domain.dto.health.HealthResponse;
import com.swpteam.smokingcessation.domain.dto.health.HealthUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Health;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HealthMapper {

    Health toEntity(HealthCreateRequest request);

    @Mapping(source = "account.id", target = "accountId")
    HealthResponse toResponse(Health health);

    void updateHealth(@MappingTarget Health entity, HealthUpdateRequest request);
}