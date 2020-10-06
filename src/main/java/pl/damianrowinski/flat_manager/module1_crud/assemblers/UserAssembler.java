package pl.damianrowinski.flat_manager.module1_crud.assemblers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.entities.User;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.common.Address;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.common.PersonNameContact;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.user.UserAddDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.user.UserListDTO;
import pl.damianrowinski.flat_manager.module1_crud.domain.model.dtos.user.UserShowDTO;

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

    public UserShowDTO getDataShowFrom(User user) {
        UserShowDTO userData = new UserShowDTO();
        userData.setLogin(user.getLogin());

        PersonNameContact userPersonContact = user.getNameContact();

        userData.setFirstName(userPersonContact.getFirstName());
        userData.setLastName(userPersonContact.getLastName());
        userData.setEmail(userPersonContact.getEmail());

        Address address = user.getAddress();
        userData.setCityName(address.getCityName());
        userData.setStreetName(address.getStreetName());
        userData.setStreetNumber(address.getStreetNumber());
        Integer apartmentNumber = address.getApartmentNumber();
        if (apartmentNumber != null)
            userData.setApartmentNumber(apartmentNumber);

        return userData;
    }

    public void setAddressUserAdd(UserAddDTO userAddDTO, User user) {
        Address address = user.getAddress();
        address.setCityName(userAddDTO.getCityName());
        address.setStreetName(userAddDTO.getStreetName());
        address.setStreetNumber(userAddDTO.getStreetNumber());
        Integer apartmentNumber = address.getApartmentNumber();
        if (apartmentNumber != null)
            address.setApartmentNumber(userAddDTO.getApartmentNumber());
        user.setAddress(address);
    }

    public User setPersonalDetailsUserAdd(UserAddDTO userAddDTO, User user) {
        PersonNameContact personalDetails = user.getNameContact();
        personalDetails.setFirstName(userAddDTO.getFirstName());
        personalDetails.setLastName(userAddDTO.getLastName());
        personalDetails.setEmail(userAddDTO.getEmail());
        user.setNameContact(personalDetails);
        return user;
    }

}
