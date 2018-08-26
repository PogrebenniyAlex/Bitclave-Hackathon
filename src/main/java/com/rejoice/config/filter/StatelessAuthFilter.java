package com.rejoice.config.filter;

import com.rejoice.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class StatelessAuthFilter extends GenericFilterBean {

    private static String PARAMETR_NAME = "access_token";

    @Autowired
    private TokenService tokenService;

    public StatelessAuthFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public StatelessAuthFilter() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        Map<String, String[]> parameterMap = request.getParameterMap();

        String[] parameters = parameterMap.get(PARAMETR_NAME);
        if(parameters != null){
            tokenService.authenticationByRequest(parameters[0]);
        }

        filterChain.doFilter(request, servletResponse);
    }
}
