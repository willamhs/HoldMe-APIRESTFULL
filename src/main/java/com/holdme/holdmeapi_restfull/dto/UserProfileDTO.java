package com.holdme.holdmeapi_restfull.dto;

import com.holdme.holdmeapi_restfull.model.enums.ERole;
import lombok.Data;

@Data
public class UserProfileDTO {
    private Integer id;
    private String email;
    private String profilePicPath;
    private ERole role; // El rol puede ser STUDENT o otro rol
    private String firstName;  // Nombre del STUDENT o otro rol
    private String lastName;  // Apellido del STUDENT o otro rol

    // Campos específicos para STUDENT
    private String occupation;
}
