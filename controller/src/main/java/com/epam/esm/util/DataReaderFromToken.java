package com.epam.esm.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Component
public class DataReaderFromToken {

    private TokenStore tokenStore;

    @Autowired
    public DataReaderFromToken(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public String getUserNameFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        return additionalInformation.get("user_name").toString();
    }

    public String getUserRoleFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> list = (List<? extends GrantedAuthority>) authentication.getAuthorities();
        return list.get(0).toString();
    }
}
