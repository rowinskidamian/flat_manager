package pl.damianrowinski.flat_manager.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.domain.entities.User;
import pl.damianrowinski.flat_manager.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.model.dtos.user.UserListDTO;

@RequiredArgsConstructor
@Component
public class UserAssembler {

    private final ModelMapper modelMapper;

    public UserListDTO getDataListFrom(User user) {
        UserListDTO userData = modelMapper.map(user, UserListDTO.class);
        PersonNameContact nameContact = user.getNameContact();
        userData.setFirstName(nameContact.getFirstName());
        userData.setFullName(nameContact.getFullName());
        return userData;
    }
}
