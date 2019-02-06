package com.virros.diplom.message.response;

import java.util.Set;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private Set<String> authorities;

    public JwtResponse(String accessToken, String username,
                       Set<String> authorities) {
        this.token = accessToken;
        this.username = username;
        this.authorities = authorities;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities){
        this.authorities = authorities;
    }
}