package com.example.empleos.security.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Esta clase es una implementación personalizada de la interfaz AuthenticationEntryPoint.
 * Delegada el manejo de excepciones de autenticación a un HandlerExceptionResolver.
 */
@Component("delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * El HandlerExceptionResolver utilizado para resolver excepciones de autenticación.
     */
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    /**
     * Inicia un esquema de autenticación.
     * <p>
     * Este método se llama cuando se lanza una excepción de autenticación.
     * Delegada el manejo de la excepción al HandlerExceptionResolver configurado.
     *
     * @param request       el HttpServletRequest
     * @param response      el HttpServletResponse
     * @param authException la excepción de autenticación
     * @throws IOException      si ocurre un error de entrada o salida
     * @throws ServletException si ocurre un error de servlet
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        resolver.resolveException(request, response, null, authException);
    }
}