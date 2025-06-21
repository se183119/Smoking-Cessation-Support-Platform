package com.swpteam.smokingcessation.feature.service.interfaces.membership;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipCreateRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipCurrencyUpdateRequest;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipResponse;
import com.swpteam.smokingcessation.domain.dto.membership.MembershipUpdateRequest;
import org.springframework.data.domain.Page;

public interface MembershipService {

    public Page<MembershipResponse> getMembershipPage(PageableRequest request);

    public MembershipResponse getMembershipById(String id);

    public MembershipResponse getMembershipByName(String name);

    public MembershipResponse createMembership(MembershipCreateRequest request);

    public MembershipResponse updateMembership(String id, MembershipUpdateRequest request);

    public void softDeleteMembershipById(String id);

    public MembershipResponse updateMembershipCurrency(String id, MembershipCurrencyUpdateRequest request);
}
