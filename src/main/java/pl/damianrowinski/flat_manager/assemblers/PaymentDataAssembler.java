package pl.damianrowinski.flat_manager.assemblers;

import pl.damianrowinski.flat_manager.domain.entities.Payment;
import pl.damianrowinski.flat_manager.domain.entities.Property;
import pl.damianrowinski.flat_manager.domain.entities.Tenant;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentEditDTO;
import pl.damianrowinski.flat_manager.model.dtos.payment.PaymentShowDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PaymentDataAssembler {

    public static PaymentEditDTO convertToPaymentEdit(Payment payment) {
        PaymentEditDTO paymentData = new PaymentEditDTO();
        paymentData.setAmount(payment.getAmount());
        paymentData.setPaymentDate(payment.getPaymentDate().toString());
        paymentData.setTenantId(payment.getTenant().getId());
        return paymentData;
    }

    public static List<PaymentShowDTO> convertToPaymentShowList(List<Payment> paymentList) {

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

    public static void setPaymentForEditSave(PaymentEditDTO paymentDataSource, Tenant tenant,  Payment paymentOutcome) {
        paymentOutcome.setTenant(tenant);
        paymentOutcome.setAmount(paymentDataSource.getAmount());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate paymentDate = LocalDate.parse(paymentDataSource.getPaymentDate(), formatter);
        paymentOutcome.setPaymentDate(paymentDate);
    }

}
