package pl.damianrowinski.flat_manager.services;

import pl.damianrowinski.flat_manager.model.dtos.UserAddDTO;

public interface AuthenticationService {

    void register(UserAddDTO userAddDTO);
}
