package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.dto.AuthResponseDTO;
import com.holdme.holdmeapi_restfull.dto.LoginDTO;
import com.holdme.holdmeapi_restfull.dto.UserProfileDTO;
import com.holdme.holdmeapi_restfull.dto.UserRegistrationDTO;
import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.exception.RoleNotFoundExeption;
import com.holdme.holdmeapi_restfull.mapper.UserMapper;
import com.holdme.holdmeapi_restfull.model.entity.Role;
import com.holdme.holdmeapi_restfull.model.entity.Customer;
import com.holdme.holdmeapi_restfull.model.entity.User;
import com.holdme.holdmeapi_restfull.model.enums.ERole;
import com.holdme.holdmeapi_restfull.repository.RoleRepository;
import com.holdme.holdmeapi_restfull.repository.CustomerRepository;
import com.holdme.holdmeapi_restfull.repository.UserRepository;
import com.holdme.holdmeapi_restfull.security.TokenProvider;
import com.holdme.holdmeapi_restfull.security.UserPrincipal;
import com.holdme.holdmeapi_restfull.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CustomerRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @Transactional
    @Override
    public UserProfileDTO registerStudent(UserRegistrationDTO registrationDTO) {
        return registerUserWithRole(registrationDTO, ERole.CUSTOMER);
    }

    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        // Autenticar al usuario utilizando AuthenticationManager
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );

        // Una vez autenticado, el objeto authentication contiene la informacion del usuario autenticado
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        User user = userPrincipal.getUser();

        String token = tokenProvider.createAccessToken(authentication);

        AuthResponseDTO responseDTO = userMapper.toAuthResponseDTO(user, token);
        return responseDTO;
    }

    @Transactional
    @Override
    public UserProfileDTO updateUserProfile(Integer id, UserProfileDTO userProfileDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado"));

        //Verificar si ya existe un estudiante con el mismo nombre y apellido (excepto el usuario actual)
        boolean existsAsStudent = studentRepository.existsByFirstNameAndLastNameAndUserIdNot(
                userProfileDTO.getFirstName(), userProfileDTO.getLastName(), id);

        if (existsAsStudent) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre y apellido");
        }

        if(user.getCustomer()!=null) {
            user.getCustomer().setFirstName(userProfileDTO.getFirstName());
            user.getCustomer().setLastName(userProfileDTO.getLastName());
            user.getCustomer().setOccupation(userProfileDTO.getOccupation());
            user.getCustomer().setUpdatedAt(LocalDateTime.now());
            user.setProfilePicPath(userProfileDTO.getProfilePicPath());
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserProfileDTO(updatedUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfileDTO getUserProfileById(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Usuario no encontrado"));

        return userMapper.toUserProfileDTO(user);
    }

    private UserProfileDTO registerUserWithRole(UserRegistrationDTO registrationDTO, ERole roleEnum) {
        //Verificar si el email ya esta registradoo o si ya existe un usuario con el mismo nombre y apellido
        boolean existsByEmail = userRepository.existsByEmail(registrationDTO.getEmail());
        boolean existsAsStudent = studentRepository.existsByFirstNameAndLastName(registrationDTO.getFirstName(), registrationDTO.getLastName());

        if (existsByEmail) {
            throw new IllegalArgumentException("El Email ya esta registrado");
        }

        if (existsAsStudent) {
            throw new IllegalArgumentException("Ya existe un usuario con el mismo nombre y apellido");
        }

        Role role = roleRepository.findByName(roleEnum)
                .orElseThrow(() -> new RoleNotFoundExeption("Error: Role is not found"));

        registrationDTO.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        User user = userMapper.toUserEntity(registrationDTO);
        user.setRole(role);

        if (roleEnum == ERole.CUSTOMER) {
            Customer customer = new Customer();
            customer.setFirstName(registrationDTO.getFirstName());
            customer.setLastName(registrationDTO.getLastName());
            customer.setOccupation(registrationDTO.getOccupation());
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUser(user);
            user.setCustomer(customer);
            user.setProfilePicPath(registrationDTO.getProfilePicPath());
        }

        User savedUser = userRepository.save(user);

        return userMapper.toUserProfileDTO(savedUser);
    }
}
