package com.curso.spring.rest;

import com.curso.spring.rest.auth.JwtAuthEntryPoint;
import com.curso.spring.rest.auth.JwtRequestFilter;
import com.curso.spring.rest.models.services.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthEntryPoint jwtAuthEntryPoint;

    private UserDetailsService userDetailsService;

    private BCryptPasswordEncoder encoder;

    private JwtRequestFilter jwtRequestFilter;

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public SecurityConfig(JwtAuthEntryPoint jwtAuthEntryPoint, JpaUserDetailsService userDetailsService, BCryptPasswordEncoder encoder,
                          JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtRequestFilter = jwtRequestFilter;
        this.encoder = encoder;
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void confGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/register", "/logout").permitAll().anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
