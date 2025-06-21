package com.swpteam.smokingcessation.feature.service.interfaces.profile;

import com.swpteam.smokingcessation.domain.dto.setting.SettingResponse;
import com.swpteam.smokingcessation.domain.dto.setting.SettingRequest;

import java.util.List;

public interface SettingService {

    SettingResponse createSetting(SettingRequest request);

    SettingResponse updateSetting(String accountId, SettingRequest request);

    void deleteSetting(String accountId);

    List<SettingResponse> getSettingList();

    SettingResponse getSetting(String accountId);
}
