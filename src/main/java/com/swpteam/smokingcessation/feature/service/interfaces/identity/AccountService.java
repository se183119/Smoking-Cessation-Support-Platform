package com.swpteam.smokingcessation.feature.service.interfaces.identity;

import com.swpteam.smokingcessation.common.PageableRequest;
import com.swpteam.smokingcessation.domain.dto.account.AccountRequest;
import com.swpteam.smokingcessation.domain.dto.account.AccountResponse;
import com.swpteam.smokingcessation.domain.dto.account.AccountUpdateRequest;
import com.swpteam.smokingcessation.domain.dto.account.ChangePasswordRequest;
import com.swpteam.smokingcessation.domain.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AccountService {

    public AccountResponse createAccount(AccountRequest request);

    public Page<AccountResponse> getAccounts(PageableRequest request);

    public AccountResponse getAccountById(String id);

    public AccountResponse updateAccountRole(String id, Role role);

    public AccountResponse updateAccountWithoutRole(String id, AccountUpdateRequest request);

    public void deleteAccount(String id);

    public AccountResponse changePassword(ChangePasswordRequest request);

    public AccountResponse getAccountByEmail(String email);

    public void banAccount(String id, Jwt jwt);
}
