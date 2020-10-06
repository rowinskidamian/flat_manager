package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.assemblers.PaymentBalanceAssembler;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceCreateDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.repositories.AccountBalanceRepository;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountBalanceService {

    private final AccountBalanceRepository accountBalanceRepository;
    private final PaymentBalanceAssembler accountBalanceAssembler;

    public void createAccountBalance(PaymentBalanceCreateDTO accountData) {

        PaymentBalance accountToSave = accountBalanceAssembler.convertAccountCreateDTO(accountData);
        accountBalanceRepository.save(accountToSave);

    }

}
