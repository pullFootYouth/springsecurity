package com.example.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class DefinedSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JWTAuthenticationFilter filter;

    public DefinedSecurityConfig() {
//        super(true);
    }

    @Bean
    public PasswordEncoder bCryptEncoder() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        return encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new JWTAuthenticationProvider());
        auth.inMemoryAuthentication().passwordEncoder(bCryptEncoder())
                .withUser("admin")
                .password("$2a$05$PXPJBORqD5COAFH0n9bpfuPVPz60vhFy/Z03S9/qtqnvcc7gwvG02")
                .roles("USER");
    }

    /**
     * can be autowired by other place in need, when define yourself filter doesn't through AbstractHttpConfigurer
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//        @Bean
    public AuthenticationManager authenticationManager(ObjectPostProcessor<Object> objectPostProcessor, PasswordEncoder bCryptEncoder) throws Exception {
        AuthenticationManagerBuilder builder = new AuthenticationManagerBuilder(objectPostProcessor);
        builder.authenticationProvider(new JWTAuthenticationProvider());
        builder.inMemoryAuthentication().passwordEncoder(bCryptEncoder)
                .withUser("admin")
                .password("$2a$05$PXPJBORqD5COAFH0n9bpfuPVPz60vhFy/Z03S9/qtqnvcc7gwvG02")
                .roles("USER");
        return builder.build();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(new JWTAccessDenyHandler()).authenticationEntryPoint(new JWTAuthenticationEntryPoint()).and()
//                .sessionManagement().and()
//                .securityContext().and()
//                .logout().and()
                .authorizeRequests().antMatchers("/user").hasAnyRole("ADMIN").anyRequest().authenticated().and()
                .csrf().disable()
                .formLogin().loginPage("/login").defaultSuccessUrl("/home").permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/test");
    }
}
