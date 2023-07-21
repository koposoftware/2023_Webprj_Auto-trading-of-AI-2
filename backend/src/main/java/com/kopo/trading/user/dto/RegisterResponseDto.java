package com.kopo.trading.user.dto;

public class RegisterResponseDto {

    private Long id;

    public RegisterResponseDto(Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
