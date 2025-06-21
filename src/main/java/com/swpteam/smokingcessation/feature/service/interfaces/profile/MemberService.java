package com.swpteam.smokingcessation.feature.service.interfaces.profile;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.member.MemberCreateRequest;
import com.swpteam.smokingcessation.domain.dto.member.MemberResponse;
import com.swpteam.smokingcessation.domain.dto.member.MemberUpdateRequest;
import org.springframework.data.domain.Page;

public interface MemberService {

    MemberResponse createMember(MemberCreateRequest request, String id);

    Page<MemberResponse> getMembers(PageableRequest request);

    MemberResponse getMemberById(String id);

    MemberResponse updateMember(MemberUpdateRequest request, String id);
}
