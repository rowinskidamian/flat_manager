package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Payment;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.exceptions.ElementNotFoundException;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.PaymentRepository;
import pl.damianrowinski.flat_manager.model.repositories.TenantRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TenantRepository tenantRepository;

    public List<PaymentShowDTO> getListOfPayments() {
        List<Payment> paymentList =
                paymentRepository.findAllByLoggedUserNameOrderByPaymentDateDesc(LoggedUsername.get());
        List<PaymentShowDTO> paymentDataList = new ArrayList<>();

        for (Payment payment : paymentList) {
            PaymentShowDTO paymentData = new PaymentShowDTO();
            paymentData.setId(payment.getId());
            paymentData.setAmount(payment.getAmount());
            paymentData.setPaymentDate(payment.getPaymentDate());

            Tenant tenant = payment.getTenant();
            paymentData.setTenantId(tenant.getId());
            paymentData.setTenantFullName(tenant.getPersonalDetails().getFullName());

            Property tenantProperty = tenant.getRoom().getProperty();
            paymentData.setPropertyId(tenantProperty.getId());
            paymentData.setPropertyWorkingName(tenantProperty.getWorkingName());

            paymentDataList.add(paymentData);
        }
        return paymentDataList;
    }

    public void save(PaymentEditDTO paymentData) {
        Payment paymentToSave = new Payment();

        Optional<Tenant> optionalTenant = tenantRepository.findById(paymentData.getTenantId());
        if (optionalTenant.isEmpty()) throw new ElementNotFoundException("Nie znalazłem najemcy o podanym id");
        Tenant tenantToAddPayment = optionalTenant.get();
        paymentToSave.setTenant(tenantToAddPayment);

        paymentToSave.setAmount(paymentData.getAmount());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate paymentDate = LocalDate.parse(paymentData.getPaymentDate(), formatter);
        paymentToSave.setPaymentDate(paymentDate);

        log.info("Attempt to save payment: " + paymentData);
        paymentRepository.save(paymentToSave);
    }
}
