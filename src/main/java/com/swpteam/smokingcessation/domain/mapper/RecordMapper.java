package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.record.RecordCreateRequest;
import com.swpteam.smokingcessation.domain.dto.record.RecordResponse;
import com.swpteam.smokingcessation.domain.dto.record.RecordUpdateRequest;
import com.swpteam.smokingcessation.domain.entity.Record;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecordMapper {

    com.swpteam.smokingcessation.domain.entity.Record toEntity(RecordCreateRequest request);

    @Mapping(source = "account.id", target = "accountId")
    RecordResponse toResponse(Record record);

    void update(@MappingTarget com.swpteam.smokingcessation.domain.entity.Record entity, RecordUpdateRequest request);
}