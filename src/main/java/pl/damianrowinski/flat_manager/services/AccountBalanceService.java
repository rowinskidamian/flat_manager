package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.AccountBalanceAssembler;
import pl.damianrowinski.flat_manager.domain.model.dtos.account_balance.AccountBalanceCreateDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.AccountBalance;
import pl.damianrowinski.flat_manager.domain.repositories.AccountBalanceRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountBalanceService {

    private final AccountBalanceRepository accountBalanceRepository;
    private final AccountBalanceAssembler accountBalanceAssembler;

    public void createAccountBalance(AccountBalanceCreateDTO accountData) {
        
        AccountBalance accountToSave = accountBalanceAssembler.convertAccountCreateDTO(accountData);
        accountBalanceRepository.save(accountToSave);

    }

}
