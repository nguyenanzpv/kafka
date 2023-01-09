package com.java.accountService.service.impl;

import com.java.accountService.entity.Account;
import com.java.accountService.model.AccountDTO;
import com.java.accountService.repository.AccountRepository;
import com.java.accountService.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public void add(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO,Account.class);
        //account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword())); //ma hoa pass
        accountRepository.save(account);

        accountDTO.setId(account.getId());
    }

    @Override
    public void update(AccountDTO accountDTO) {
        Account account = accountRepository.getById(accountDTO.getId());
        if(account != null){
            modelMapper.typeMap(AccountDTO.class,Account.class)
                    .addMappings(mapper -> mapper.skip(Account::setPassword)).map(accountDTO,account);
            accountRepository.save(account);
        }
    }

    @Override
    public void updatePassword(AccountDTO accountDTO) {
        Account account = accountRepository.getById(accountDTO.getId());
        if(account != null){
            //account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));
            account.setPassword(accountDTO.getPassword());
            accountRepository.save(account);
        }

    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.getById(id);
        if(account != null){
            accountRepository.delete(account);
        }
    }

    @Override
    public List<AccountDTO> getAll() {
        List<AccountDTO> accountDTOS = new ArrayList<AccountDTO>();
        accountRepository.findAll().forEach((account) ->{
            accountDTOS.add(modelMapper.map(account,AccountDTO.class));
        });
        return  accountDTOS;
    }

    @Override
    public AccountDTO getOne(Long id) {
        Account account = accountRepository.getById(id);
        if(account != null){
            return modelMapper.map(account,AccountDTO.class);
        }
        return null;
    }
}
