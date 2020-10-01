package pl.damianrowinski.flat_manager.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.damianrowinski.flat_manager.domain.entities.Payment;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.PaymentShowDTO;
import pl.damianrowinski.flat_manager.model.repositories.PaymentRepository;
import pl.damianrowinski.flat_manager.utils.LoggedUsername;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;

    public List<PaymentShowDTO> getListOfPayments() {
        List<Payment> paymentList = paymentRepository.findAllByLoggedUserName(LoggedUsername.get());
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
}
