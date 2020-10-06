package pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.tenant;

import lombok.Data;

@Data
public class TenantDeleteDTO {
    private Long id;
    private Long roomId;
    private String loggedUserName;
}
