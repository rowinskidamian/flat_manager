package pl.damianrowinski.flat_manager.model.dtos.tenant;

import lombok.Data;

@Data
public class TenantListDTO {

    private Long tenantId;
    private String tenantFullName;
    private String loggedUserName;
}
