package com.holdme.holdmeapi_restfull.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PsychologistDTO {
    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @Email
    @NotEmpty
    private String email;

    private String phoneNumber;

    private String specialty;

    private String bio;

    @NotNull
    private Boolean active;

    private List<String> availableHours;

}
