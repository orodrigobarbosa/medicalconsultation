package com.medicalconsultation.demo.config;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity //habilita as configuracoes de seguranda da aplicacao
public class SecurityConfig {

    //metodo que filtra os metodos de requisicoes
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //tratamento de permissões para os endpoints de Usuario
                        .requestMatchers(HttpMethod.POST, "/usuario").hasAnyRole("ADMIN", "SECRETARIO")
                        .requestMatchers(HttpMethod.PUT, "/usuario/**").hasAnyRole("ADMIN", "SECRETARIO")
                        .requestMatchers(HttpMethod.DELETE, "/usuario/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuario/**").permitAll()

                        //tratamento de permissões para os endpoints de Consulta
                        .requestMatchers(HttpMethod.PUT, "/consulta/**").hasAnyRole("ADMIN", "SECRETARIO")
                        .requestMatchers(HttpMethod.DELETE, "/consulta/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/consulta/**").permitAll()


                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { //paramentro na funcao para gerenciar usuario
        //utilizado para gerenciar senhas do usuario
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        //
        auth.inMemoryAuthentication()
                .withUser("Rodrigo").password(passwordEncoder.encode("12345")).roles("ADMIN")
                .and()
                .withUser("Silva").password(passwordEncoder.encode("123")).roles("SECRETARIO")
                .and()
                .withUser("Cicrano").password(passwordEncoder.encode("1234")).roles("PACIENTE");
    }

}
