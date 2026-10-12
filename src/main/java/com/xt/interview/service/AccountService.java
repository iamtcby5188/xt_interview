package com.xt.interview.service;

import com.xt.interview.dto.AccountDTO;
import com.xt.interview.dto.PageDTO;

import java.util.List;

public interface AccountService {

    AccountDTO createAccount(AccountDTO dto);

    AccountDTO updateAccount(Long id, AccountDTO dto);

    void deleteAccount(Long id);

    AccountDTO getAccountById(Long id);

    List<AccountDTO> getAllAccounts();

    AccountDTO login(String username, String password);

    PageDTO<AccountDTO> getPageAccounts(String username, Integer status, Integer roleLevel, int page, int size);
}
