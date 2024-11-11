package com.holdme.holdmeapi_restfull.dto;

import com.holdme.holdmeapi_restfull.model.enums.ERole;
import lombok.Data;

@Data
public class AuthResponseDTO {
    private Integer id;         //El id del user
    private String token;       //El token JWT
    private String firstName;   //El primer nombre del usuario
    private String lastName;    //El apellido del usuario
    private String role;        //El rol del usuario
    private Integer customerId;  //El id del cliente
}
