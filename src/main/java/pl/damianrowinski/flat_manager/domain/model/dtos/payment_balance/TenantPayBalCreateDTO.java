package pl.damianrowinski.flat_manager.domain.model.dtos.payment_balance;

import lombok.Data;

@Data
public class TenantPayBalCreateDTO {

    private Double roomRent;
    private Long tenantId;
    private Long propertyId;
    private String tenantName;
    private String propertyName;
}
