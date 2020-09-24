package pl.damianrowinski.flat_manager.model.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PropertyDeleteDTO {
    private Long id;
    private List<Long> tenantsIdsList;
}
