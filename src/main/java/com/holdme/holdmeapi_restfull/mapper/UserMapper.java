package com.holdme.holdmeapi_restfull.mapper;

import com.holdme.holdmeapi_restfull.dto.AuthResponseDTO;
import com.holdme.holdmeapi_restfull.dto.LoginDTO;
import com.holdme.holdmeapi_restfull.dto.UserProfileDTO;
import com.holdme.holdmeapi_restfull.dto.UserRegistrationDTO;
import com.holdme.holdmeapi_restfull.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper modelMapper;

    public User toUserEntity(UserRegistrationDTO registrationDTO) {
        return modelMapper.map(registrationDTO, User.class);
    }

    public UserProfileDTO toUserProfileDTO(User user) {
        UserProfileDTO userProfileDTO = modelMapper.map(user, UserProfileDTO.class);

        if(user.getCustomer() != null) {
            userProfileDTO.setFirstName(user.getCustomer().getFirstName());
            userProfileDTO.setLastName(user.getCustomer().getLastName());
            userProfileDTO.setOccupation(user.getCustomer().getOccupation());
            userProfileDTO.setProfilePicPath(user.getProfilePicPath());
        }
        return userProfileDTO;
    }

    //Convertir de LoginDTO a User(cuando se procesa el login)
    public User toUserEntity(LoginDTO loginDTO) {
        return modelMapper.map(loginDTO, User.class);
    }

    //Convertir de User a AuthResponseDTO para la respuesta de autenticacion
    public AuthResponseDTO toAuthResponseDTO(User user, String token) {
        AuthResponseDTO authResponseDTO = new AuthResponseDTO();

        authResponseDTO.setId(user.getId());
        authResponseDTO.setToken(token);

        //Obtener el nombre y apellido
        String firstName = (user.getCustomer() != null) ? user.getCustomer().getFirstName()
                : "Admin";
        String lastName = (user.getCustomer() != null) ? user.getCustomer().getLastName()
                : "User";

        authResponseDTO.setFirstName(firstName);
        authResponseDTO.setLastName(lastName);
        authResponseDTO.setCustomerId(user.getCustomer().getId());
        authResponseDTO.setRole(user.getRole().getName().name());

        return authResponseDTO;
    }
}
