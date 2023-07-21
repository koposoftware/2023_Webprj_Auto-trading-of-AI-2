package com.kopo.trading.user.dto;

import com.kopo.trading.user.entity.Member;

public class RegisterDto {

    String emailAddress;
    String password;
    String nickname;

    public RegisterDto(String emailAddress, String password, String nickname) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.nickname = nickname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public Member toEntity() {
        return new Member(emailAddress, password);
    }

}
