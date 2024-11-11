package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.dto.AuthResponseDTO;
import com.holdme.holdmeapi_restfull.dto.LoginDTO;
import com.holdme.holdmeapi_restfull.dto.UserProfileDTO;
import com.holdme.holdmeapi_restfull.dto.UserRegistrationDTO;

public interface UserService {
    // Register a Student
    UserProfileDTO registerStudent(UserRegistrationDTO registrationDTO);

    // Si hubiera otro ERole, agregaria más tipos de registration para cada uno de estos

    // Actualizar el perfil de usuario
    UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO);

    // Authenticate user(login)
    AuthResponseDTO login(LoginDTO loginDTO);

    // Get user profile by ID
    UserProfileDTO getUserProfileById(Integer id);
}
