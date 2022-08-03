package com.example.demo.configuration;

import com.example.demo.Controller.RestTransactionsController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.example.demo.Constants.EndPoints.*;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    Logger logger = LoggerFactory.getLogger(RestTransactionsController.class);
    @Autowired
    CustomAuthenticationProvider customAuthenticationProvider;


    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        logger.trace("Entered Authentication Manager");
        authenticationManagerBuilder.authenticationProvider(customAuthenticationProvider);
    }


    @Override
    public void  configure(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(LOGIN,STYLES,LOGIN_FAIL_URL,"/**").permitAll()

                .anyRequest()
                .authenticated()
                .and().httpBasic()
                .and()
                .formLogin().loginProcessingUrl(PROCESS_LOGIN).defaultSuccessUrl(HOME)
                .and()
                .logout()
                ;
    }

}
