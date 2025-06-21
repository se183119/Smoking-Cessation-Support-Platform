package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.entity.Setting;
import com.swpteam.smokingcessation.domain.dto.setting.SettingRequest;
import com.swpteam.smokingcessation.domain.dto.setting.SettingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SettingMapper {
    Setting toEntity(SettingRequest request);

    SettingResponse toResponse(Setting setting);

    void update(@MappingTarget Setting entity, SettingRequest request);
}
