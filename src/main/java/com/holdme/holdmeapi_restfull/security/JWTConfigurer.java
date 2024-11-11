package com.holdme.holdmeapi_restfull.security;


import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // Todo: Crear una instancia del filtro JWTFilter, pasandole el TokenProvider que manejará la logica del JWT
        JWTFilter jwtFilter = new JWTFilter(tokenProvider);

        // Todo: Agregar el filtro JWTFilter a la cadena de seguridad de Spring Security, asegurando que
        //  se ejecute antes del filtro de autenticacion de usuario y contraseña (UsernamePasswordAuthenticationFilter)
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
