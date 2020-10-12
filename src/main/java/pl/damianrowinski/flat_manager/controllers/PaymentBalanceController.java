package pl.damianrowinski.flat_manager.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;
import pl.damianrowinski.flat_manager.domain.repositories.PaymentBalanceRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PaymentBalanceController {

    private final PaymentBalanceRepository paymentBalanceRepository;

    @RequestMapping("/paymentbalance")
    @ResponseBody
    public String balanceForProperties() {
        List<PaymentBalance> listLatestBalanceProperty = paymentBalanceRepository
                .getListLatestBalanceFor(PaymentBalanceType.PROPERTY);
        listLatestBalanceProperty.forEach(paymentBalance -> log.info(paymentBalance.toString()));
        return "";
    }
}
