package pl.damianrowinski.flat_manager.module1_crud.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferType;
import pl.damianrowinski.flat_manager.module1_crud.assemblers.TenantAssembler;
import pl.damianrowinski.flat_manager.app_common.dtos.TenantTransferDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.Tenant;
import pl.damianrowinski.flat_manager.module1_crud.module_senders.TenantSender;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

public class TenantTransferListener {

    private TenantAssembler tenantAssembler;
    private TenantSender tenantSender;

    @PostPersist
    public void createTenantAccount(Tenant tenant) {
        TenantTransferDTO tenantData = tenantAssembler.convertToTransferData(tenant);
        tenantData.setTransferType(TenantTransferType.CREATE);
        tenantSender.send(tenantData);
    }

    @PostUpdate
    public void updateTenantAccount(Tenant tenant) {
        TenantTransferDTO tenantData = tenantAssembler.convertToTransferData(tenant);
        tenantData.setTransferType(TenantTransferType.UPDATE);
        tenantSender.send(tenantData);
    }

    @Autowired
    public void setTenantAssembler(TenantAssembler tenantAssembler) {
        this.tenantAssembler = tenantAssembler;
    }

    @Autowired
    public void setTenantSender(TenantSender tenantSender) { this.tenantSender = tenantSender; }
}
