package com.swpteam.smokingcessation.feature.service.impl.membership;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.SubscriptionMapper;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.entity.Membership;
import com.swpteam.smokingcessation.feature.repository.MembershipRepository;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionRequest;
import com.swpteam.smokingcessation.domain.dto.subscription.SubscriptionResponse;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Subscription;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.feature.repository.SubscriptionRepository;
import com.swpteam.smokingcessation.feature.service.interfaces.membership.SubscriptionService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    SubscriptionRepository subscriptionRepository;
    SubscriptionMapper subscriptionMapper;
    AccountRepository accountRepository;
    MembershipRepository membershipRepository;

    @Override
    public Page<SubscriptionResponse> getSubscriptionPage(PageableRequest request) {
        if (!ValidationUtil.isFieldExist(Subscription.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Subscription> subscriptions = subscriptionRepository.findAllByIsDeletedFalse(pageable);

        return subscriptions.map(subscriptionMapper::toResponse);
    }

    @Override
    public SubscriptionResponse getSubscriptionById(String id) {
        return subscriptionMapper.toResponse(
                subscriptionRepository.findByIdAndIsDeletedFalse(id)
                        .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_FOUND)));
    }

    @Override
    public Page<SubscriptionResponse> getSubscriptionPageByAccountId(String accountId, PageableRequest request) {
        if (!ValidationUtil.isFieldExist(Subscription.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Subscription> subscriptions = subscriptionRepository.findByAccountIdAndIsDeletedFalse(accountId, pageable);

        return subscriptions.map(subscriptionMapper::toResponse);
    }

    @Override
    @Transactional
    public Subscription createSubscription(String accountId, String membershipName) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Membership membership = membershipRepository.findByNameAndIsDeletedFalse(membershipName)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBERSHIP_NOT_FOUND));

        Subscription subscription = Subscription.builder()
                .account(account)
                .membership(membership)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(membership.getDurationDays()))
                .build();

        return subscriptionRepository.save(subscription);
    }

    @Override
    @Transactional
    public SubscriptionResponse createSubscription(SubscriptionRequest request) {
        Subscription subscription = subscriptionMapper.toEntity(request);

        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        Membership membership = membershipRepository.findByNameAndIsDeletedFalse(request.getMembershipName())
                .orElseThrow(() -> new AppException(ErrorCode.MEMBERSHIP_NOT_FOUND));

        subscription.setAccount(account);
        subscription.setMembership(membership);

        return subscriptionMapper.toResponse(subscriptionRepository.save(subscription));
    }

    @Override
    @Transactional
    public SubscriptionResponse updateSubscription(String id, SubscriptionRequest request) {
        Subscription subscription = subscriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_FOUND));

        subscriptionMapper.update(subscription, request);

        return subscriptionMapper.toResponse(subscriptionRepository.save(subscription));
    }

    @Override
    @Transactional
    public void deleteSubscription(String id) {
        Subscription subscription = subscriptionRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.SUBSCRIPTION_NOT_FOUND));

        subscription.setDeleted(true);

        subscriptionRepository.save(subscription);
    }
}
