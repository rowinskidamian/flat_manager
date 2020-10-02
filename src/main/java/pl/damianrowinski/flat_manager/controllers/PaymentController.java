package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentShowDTO;
import pl.damianrowinski.flat_manager.model.dtos.tenant.TenantEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.tenant.TenantListDTO;
import pl.damianrowinski.flat_manager.services.PaymentService;
import pl.damianrowinski.flat_manager.services.PropertyService;
import pl.damianrowinski.flat_manager.services.TenantService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")

public class PaymentController {

    private final PaymentService paymentService;
    private final TenantService tenantService;
    private final PropertyService propertyService;

    @RequestMapping
    public String redirectToList() {
        return "redirect:/payment/list";
    }

    @GetMapping("/list")
    public String generateList(Model model) {
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
        if (result.hasErrors()) {
            model.addAttribute("paymentData", paymentData);
            return "/payment/form";
        }
        paymentService.save(paymentData);
        return "redirect:/payment";
    }

    @GetMapping("/edit/{paymentId}")
    public String editPaymentGenerate(@PathVariable Long paymentId, Model model) {
        PaymentEditDTO paymentToEdit = paymentService.findPaymentToEdit(paymentId);
        model.addAttribute("paymentData", paymentToEdit);
        return "/payment/form_edit";
    }

    @PostMapping("/edit/{paymentId}")
    public String editPayment(@PathVariable Long paymentId, @ModelAttribute("paymentData") @Valid PaymentEditDTO
            paymentData, BindingResult result, Model model) {
        paymentData.setId(paymentId);
        if (result.hasErrors()) {
            model.addAttribute("paymentData", paymentData);
            return "/payment/form_edit";
        }
        paymentService.edit(paymentData);
        return "redirect:/payment";
    }

    @GetMapping("/delete/{paymentId}")
    public String deleteGenerate(@PathVariable Long paymentId, Model model) {
        paymentService.getPaymentOrThrow(paymentId);
        model.addAttribute("paymentDeleteId", paymentId);
        return "/payment/delete_confirmation";
    }

    @PostMapping("/delete/{paymentId}")
    public String deletePayment(Long paymentDeleteId) {
        paymentService.delete(paymentDeleteId);
        return "redirect:/payment";
    }

    @GetMapping("/show/for_tenant/{tenantId}")
    public String showPaymentForTenant(@PathVariable Long tenantId, Model model) {
        List<PaymentShowDTO> paymentsDataList = paymentService.findPaymentsForTenant(tenantId);
        PaymentShowDTO paymentShowDTO;
        String title = "";

        if (paymentsDataList.size() > 0) {
            paymentShowDTO = paymentsDataList.get(0);
            title = paymentShowDTO.getTenantFullName();
        }

        model.addAttribute("title", title);
        model.addAttribute("paymentsList", paymentsDataList);
        return "/payment/list";
    }

    @GetMapping("/show/for_property/{propertyId}")
    public String showPaymentForProperty(@PathVariable Long propertyId, Model model) {
        List<PaymentShowDTO> paymentsDataList = paymentService.findPaymentsForProperty(propertyId);
        PaymentShowDTO paymentShowDTO;
        String title = "";

        if (paymentsDataList.size() > 0) {
            paymentShowDTO = paymentsDataList.get(0);
            title = paymentShowDTO.getPropertyWorkingName();
        }

        model.addAttribute("title", title);
        model.addAttribute("paymentsList", paymentsDataList);
        return "/payment/list";
    }


}
