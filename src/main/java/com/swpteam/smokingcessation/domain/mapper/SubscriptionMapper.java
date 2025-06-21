package com.swpteam.smokingcessation.domain.mapper;

import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionResponse;
import com.swpteam.smokingcessation.domain.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    Subscription toEntity(SubscriptionRequest request);

    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "membership.name", target = "membershipName")
    SubscriptionResponse toResponse(Subscription Subscription);

    void update(@MappingTarget Subscription Subscription, SubscriptionRequest request);
}
