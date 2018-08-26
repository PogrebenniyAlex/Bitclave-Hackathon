package com.rejoice.config;

import com.rejoice.config.filter.StatelessAuthFilter;
import com.rejoice.service.TokenService;
import com.rejoice.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.query.spi.EvaluationContextExtension;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(new StatelessAuthFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .antMatcher("/**").authorizeRequests()
                .antMatchers( "/login/**", "/webjars/**", "/signup/**", "/signin/**").permitAll()
                //.antMatchers("/").authenticated()
                .anyRequest().permitAll()
                    .and()
                .formLogin().loginProcessingUrl("signin/form")
                    .and()
                .exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                    .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
                    .and()
                .csrf().disable();
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
                /*.and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);*/
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, UserDetailsServiceImpl userDetailsServiceImpl) throws Exception {
        auth
                .userDetailsService(userDetailsServiceImpl);
    }

    @Bean
    EvaluationContextExtension securityExtension() {
        return new SecurityEvaluationContextExtension();
    }


}
