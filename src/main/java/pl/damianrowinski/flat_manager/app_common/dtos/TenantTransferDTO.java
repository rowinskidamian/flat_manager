package pl.damianrowinski.flat_manager.app_common.dtos;

import lombok.Data;

@Data
public class TenantTransferDTO {

    private Double roomRent;
    private Long tenantId;
    private String tenantName;
    private TenantTransferType transferType;
}
