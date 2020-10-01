package pl.damianrowinski.flat_manager.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.damianrowinski.flat_manager.model.dtos.PaymentShowDTO;
import pl.damianrowinski.flat_manager.services.PaymentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")

public class PaymentController {

    private final PaymentService paymentService;

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
}
