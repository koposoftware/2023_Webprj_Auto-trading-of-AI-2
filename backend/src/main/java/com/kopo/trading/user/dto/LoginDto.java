package com.kopo.trading.user.dto;

public class LoginDto {

    String emailAddress;
    String password;

    public LoginDto(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;
    }

    public String getPassword() {return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
