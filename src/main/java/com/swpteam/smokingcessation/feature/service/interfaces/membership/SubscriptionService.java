package com.swpteam.smokingcessation.feature.service.interfaces.membership;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionResponse;
import com.swpteam.smokingcessation.domain.entity.Subscription;
import org.springframework.data.domain.Page;

public interface SubscriptionService {

    public Page<SubscriptionResponse> getSubscriptionPage(PageableRequest request);

    public SubscriptionResponse getSubscriptionById(String id);

    public Page<SubscriptionResponse> getSubscriptionPageByAccountId(String accountId, PageableRequest request);

    public Subscription createSubscription(String accountId, String membershipName);

    public SubscriptionResponse createSubscription(SubscriptionRequest request);

    public SubscriptionResponse updateSubscription(String id, SubscriptionRequest request);

    public void deleteSubscription(String id);
}
