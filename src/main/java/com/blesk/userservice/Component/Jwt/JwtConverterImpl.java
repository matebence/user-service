package com.blesk.userservice.Component.Jwt;

import com.blesk.userservice.DTO.Mapper.JwtMapper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JwtConverterImpl extends DefaultAccessTokenConverter implements JwtConverter {

    @Override
    public void configure(JwtAccessTokenConverter converter) {
        converter.setAccessTokenConverter(this);
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication oAuth2Authentication = super.extractAuthentication(map);
        JwtMapper jwtMapper = new JwtMapper();

        if (map.get("account_id") != null)
            jwtMapper.setAccount_id((Integer) map.get("account_id"));

        if (map.get("user_name") != null)
            jwtMapper.setUser_name((String) map.get("user_name"));

        if (map.get("exp") != null)
            jwtMapper.setExp((Long) map.get("exp"));

        if (map.get("jti") != null)
            jwtMapper.setJti((String) map.get("jti"));

        if (map.get("client_id") != null)
            jwtMapper.setClient_id((String) map.get("client_id"));

        oAuth2Authentication.setDetails(jwtMapper);
        return oAuth2Authentication;
    }
}