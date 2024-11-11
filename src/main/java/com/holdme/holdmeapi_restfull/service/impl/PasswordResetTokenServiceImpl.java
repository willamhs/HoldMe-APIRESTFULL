package com.holdme.holdmeapi_restfull.service.impl;

import com.holdme.holdmeapi_restfull.exception.ResourceNotFoundException;
import com.holdme.holdmeapi_restfull.integration.notification.email.dto.Mail;
import com.holdme.holdmeapi_restfull.integration.notification.email.service.EmailService;
import com.holdme.holdmeapi_restfull.model.entity.PasswordResetToken;
import com.holdme.holdmeapi_restfull.model.entity.User;
import com.holdme.holdmeapi_restfull.repository.PasswordResetTokenRepository;
import com.holdme.holdmeapi_restfull.repository.UserRepository;
import com.holdme.holdmeapi_restfull.service.PasswordResetTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username")
    private String mailFrom;

    @Transactional
    @Override
    public void createAndSendPasswordResetToken(String email) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiration(10);
        passwordResetTokenRepository.save(passwordResetToken);

        Map<String, Object> model = new HashMap<>();
        String resetUrl = "http://localhost:4200/#/forgot/" + passwordResetToken.getToken();
        model.put("user", user.getEmail());
        model.put("resetUrl", resetUrl);

        Mail mail = emailService.createMail(
                user.getEmail(),
                "Restablecer Contraseña",
                model,
                mailFrom
        );

        emailService.sendMail(mail, "email/password-reset-template");

    }

    @Transactional(readOnly = true)
    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token de restablecimiento de contraseña no encontrado"));
    }

    @Transactional(readOnly = true)
    @Override
    public PasswordResetToken findByUserId(Integer userId) {
        return passwordResetTokenRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("UserId no encontrado"));
    }

    @Override
    public boolean isValidToken(String token) {
        return passwordResetTokenRepository.findByToken(token)
                .filter(t->!t.isExpired())
                .isPresent();
    }

    @Transactional
    @Override
    public void removeResetToken(String token) {
        PasswordResetToken passwordResetToken = findByToken(token);
        passwordResetTokenRepository.delete(passwordResetToken);
    }

    @Transactional
    @Override
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .filter(t->!t.isExpired())
                .orElseThrow(() -> new ResourceNotFoundException("Token de restablecimiento de contraseña invalido o expirado"));

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        passwordResetTokenRepository.delete(resetToken);

    }
}
