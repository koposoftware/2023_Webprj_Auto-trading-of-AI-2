package com.kopo.trading.user.dto;

public class OauthDto {
    private String provider;
    private String accessToken;
    private String nonce;

    public OauthDto() {
    }

    public OauthDto(String provider, String accessToken, String nonce) {
        this.provider = provider;
        this.accessToken = accessToken;
        this.nonce = nonce;
    }

    public OauthDto(String provider, String accessToken) {
        this.provider = provider;
        this.accessToken = accessToken;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
