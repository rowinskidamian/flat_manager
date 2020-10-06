package pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.tenant;

import lombok.Data;

@Data
public class TenantListDTO {

    private Long tenantId;
    private Long roomId;
    private String tenantFullName;
    private String loggedUserName;
}
