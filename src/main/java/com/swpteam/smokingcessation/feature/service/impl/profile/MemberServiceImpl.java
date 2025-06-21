package com.swpteam.smokingcessation.feature.service.impl.profile;

import com.swpteam.smokingcessation.domain.entity.Account;
import com.swpteam.smokingcessation.domain.mapper.MemberMapper;
import com.swpteam.smokingcessation.feature.repository.AccountRepository;
import com.swpteam.smokingcessation.domain.dto.member.MemberCreateRequest;
import com.swpteam.smokingcessation.domain.dto.member.MemberResponse;
import com.swpteam.smokingcessation.domain.dto.member.MemberUpdateRequest;
import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.constant.ErrorCode;
import com.swpteam.smokingcessation.domain.entity.Member;
import com.swpteam.smokingcessation.exception.AppException;
import com.swpteam.smokingcessation.feature.repository.MemberRepository;
import com.swpteam.smokingcessation.feature.service.interfaces.profile.MemberService;
import com.swpteam.smokingcessation.utils.ValidationUtil;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MemberServiceImpl implements MemberService {

    MemberMapper memberMapper;
    MemberRepository memberRepository;
    AccountRepository accountRepository;

    @Override
    public MemberResponse createMember(MemberCreateRequest request, String id) {
        Account account = accountRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));


        if (memberRepository.existsByFullName(request.getFullName())) {
            throw new AppException(ErrorCode.MEMBER_EXISTED);
        }

        Member member = memberMapper.toEntity(request);
        member.setAccount(account);

        return memberMapper.toResponse(memberRepository.save(member));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Page<MemberResponse> getMembers(PageableRequest request) {
        if (!ValidationUtil.isFieldExist(Member.class, request.getSortBy())) {
            throw new AppException(ErrorCode.INVALID_SORT_FIELD);
        }

        Pageable pageable = PageableRequest.getPageable(request);
        Page<Member> members = memberRepository.findAllByIsDeletedFalse(pageable);

        return members.map(memberMapper::toResponse);
    }

    @Override
    public MemberResponse getMemberById(String id) {
        return memberMapper.toResponse(findMemberById(id));
    }

    private Member findMemberById(String id) {
        Member member = memberRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND));
        if (member.getAccount().isDeleted()) {
            throw new AppException(ErrorCode.ACCOUNT_DELETED);
        }
        return member;
    }

    @Override
    @Transactional
    public MemberResponse updateMember(MemberUpdateRequest request, String id) {
        Member member = findMemberById(id);

        memberMapper.updateMember(member, request);
        memberRepository.save(member);

        return memberMapper.toResponse(member);
    }
}
