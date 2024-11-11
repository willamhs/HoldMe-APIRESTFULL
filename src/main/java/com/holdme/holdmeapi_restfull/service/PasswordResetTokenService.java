package com.holdme.holdmeapi_restfull.service;

import com.holdme.holdmeapi_restfull.model.entity.PasswordResetToken;

public interface PasswordResetTokenService {

    void createAndSendPasswordResetToken(String email) throws Exception;
    PasswordResetToken findByToken(String token);
    PasswordResetToken findByUserId(Integer userId);
    void removeResetToken(String token);
    boolean isValidToken(String token);
    void resetPassword(String token, String newPassword);

}
