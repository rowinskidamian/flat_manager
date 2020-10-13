package pl.damianrowinski.flat_manager.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance.PaymentBalanceShowDTO;
import pl.damianrowinski.flat_manager.domain.model.dtos.tenant.TenantShowDTO;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalance;
import pl.damianrowinski.flat_manager.domain.model.entities.PaymentBalanceType;
import pl.damianrowinski.flat_manager.domain.repositories.PaymentBalanceRepository;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.services.PaymentBalanceService;
import pl.damianrowinski.flat_manager.services.TenantService;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payment_balance")
public class PaymentBalanceController {

    private final PaymentBalanceService paymentBalanceService;
    private final TenantService tenantService;

    @RequestMapping
    public String balanceForAllUnits(Model model) {

        List<PaymentBalanceShowDTO> userBalance = paymentBalanceService.getCurrentUserBalance();

        List<PaymentBalanceShowDTO> propertiesBalances = paymentBalanceService.getCurrentPropertiesBalances();

        List<PaymentBalanceShowDTO> tenantsBalances = paymentBalanceService.getCurrentTenantsBalances();

        model.addAttribute("userBalance", userBalance.get(0));
        model.addAttribute("propertyBalanceList", propertiesBalances);
        model.addAttribute("tenantBalanceList", tenantsBalances);

        return "/payment_balance/list";

    }

    @RequestMapping("/collectrent")
    @ResponseBody
    public String collectRent() {
        List<TenantShowDTO> tenantList = tenantService.findAllLoggedUser(LoggedUsername.get());
        paymentBalanceService.collectRentFromTenants(tenantList);
        return "";
    }
}
