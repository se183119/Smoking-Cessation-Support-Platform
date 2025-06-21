package com.swpteam.smokingcessation.feature.api.v1.profile;

import com.swpteam.smokingcessation.domain.dto.setting.SettingRequest;
import com.swpteam.smokingcessation.domain.dto.setting.SettingResponse;
import com.swpteam.smokingcessation.common.ApiResponse;
import com.swpteam.smokingcessation.feature.service.interfaces.profile.SettingService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SettingController {

    SettingService settingService;

    @PostMapping
    ApiResponse<SettingResponse> createSetting(@RequestBody @Valid SettingRequest request) {
        return ApiResponse.<SettingResponse>builder()
                .result(settingService.createSetting(request))
                .build();
    }

    @PutMapping("/{accountId}")
    ApiResponse<SettingResponse> updateSetting(@PathVariable String accountId, @RequestBody @Valid SettingRequest request) {
        return ApiResponse.<SettingResponse>builder()
                .result(settingService.updateSetting(accountId, request))
                .build();
    }

    @DeleteMapping("/{accountId}")
    ApiResponse<String> createSetting(@PathVariable String accountId) {
        settingService.deleteSetting(accountId);
        return ApiResponse.<String>builder()
                .result("Setting has been deleted")
                .build();
    }

    @GetMapping
    ApiResponse<List<SettingResponse>> getSettingList() {
        return ApiResponse.<List<SettingResponse>>builder()
                .result(settingService.getSettingList())
                .build();
    }

    @GetMapping("/{accountId}")
    ApiResponse<SettingResponse> getSettingList(@PathVariable String accountId) {
        return ApiResponse.<SettingResponse>builder()
                .result(settingService.getSetting(accountId))
                .build();
    }
}
