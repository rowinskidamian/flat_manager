package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.tenant.TenantListDTO;
import pl.damianrowinski.flat_manager.services.PaymentService;
import pl.damianrowinski.flat_manager.services.RoomService;
import pl.damianrowinski.flat_manager.services.TenantService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")

public class PaymentController {

    private final PaymentService paymentService;
    private final TenantService tenantService;

    @RequestMapping
    public String redirectToList() {
        return "redirect:/payment/list";
    }

    @GetMapping("/list")
    public String generateList(Model model){
        List<PaymentShowDTO> paymentsList = paymentService.getListOfPayments();
        model.addAttribute("paymentsList", paymentsList);
        return "/payment/list";
    }

    @ModelAttribute("tenantList")
    public List<TenantListDTO> getTenantList() {
        return tenantService.findAll();
    }

    @GetMapping("/add")
    public String addPaymentGenerate(Model model) {
        PaymentEditDTO paymentToAdd = new PaymentEditDTO();
        model.addAttribute("paymentData", paymentToAdd);
        return "/payment/form";
    }

    @PostMapping("/add")
    public String addPayment(@ModelAttribute("paymentData") @Valid PaymentEditDTO paymentData, BindingResult result,
                             Model model) {
        if(result.hasErrors()) {
            model.addAttribute("paymentData", paymentData);
            return "/payment/form";
        }
        paymentService.save(paymentData);
        return "redirect:/payment";
    }
}
