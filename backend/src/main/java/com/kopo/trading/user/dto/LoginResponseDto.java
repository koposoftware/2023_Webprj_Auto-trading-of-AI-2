package com.kopo.trading.user.dto;

import java.util.Date;

public class LoginResponseDto {
    private String emailAddress;
    private String token;
    private Date tokenExpire;
    private String refreshToken;
    private Date refreshTokenExpire;

    public LoginResponseDto(String emailAddress, String token, Date tokenExpire, String refreshToken, Date refreshTokenExpire) {
        this.emailAddress = emailAddress;
        this.token = token;
        this.tokenExpire = tokenExpire;
        this.refreshToken = refreshToken;
        this.refreshTokenExpire = refreshTokenExpire;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenExpire() {
        return tokenExpire;
    }

    public void setTokenExpire(Date tokenExpire) {
        this.tokenExpire = tokenExpire;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Date getRefreshTokenExpire() {
        return refreshTokenExpire;
    }

    public void setRefreshTokenExpire(Date refreshTokenExpire) {
        this.refreshTokenExpire = refreshTokenExpire;
    }
}
