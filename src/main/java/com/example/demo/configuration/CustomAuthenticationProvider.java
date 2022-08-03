package com.example.demo.configuration;

import com.example.demo.Controller.RestTransactionsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

import static com.example.demo.Constants.EndPoints.EXTERNAL_API_URL;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    Logger logger = LoggerFactory.getLogger(RestTransactionsController.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.trace("Entered Authentication provider ");
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        LoginResponse loginResponse = shouldAuthenticateAgainstThirdPartySystem(name,password);
        if ((loginResponse.getSuccess()) ){
            return new UsernamePasswordAuthenticationToken(name, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException(loginResponse.getErrorMessage());
        }
    }

    private LoginResponse shouldAuthenticateAgainstThirdPartySystem(String username, String password) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        RestTemplate restTemplate = new RestTemplate();
        LoginResponse loginResponse1=new LoginResponse();

        ResponseEntity<LoginResponse> loginResponse = restTemplate.postForEntity(EXTERNAL_API_URL,user,LoginResponse.class);
        return loginResponse.getBody();


    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}