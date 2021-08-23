package com.java.springdatajpaapplication.configuration;

import com.java.springdatajpaapplication.security.CustomAuthenticationFilter;
import com.java.springdatajpaapplication.security.CustomAuthorizationFilter;
import com.java.springdatajpaapplication.security.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JWTProvider jwtProvider;

    public SecurityConfiguration(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService, JWTProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
    }

    //Used in case we need to override the randomUUID pass provided by spring security
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

        /*
        This is going to be for some user auth with basic auth
        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("user1")
                .password(passwordEncoder.encode("user1pass"))
                .roles("STUDENT")
                .authorities("ROLE_STUDENT");

        authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("user2")
                .password(passwordEncoder.encode("user2pass"))
                .roles("TEACHER")
                .authorities("ROLE_TEACHER");

         */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/login/*").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /*
    //Declaring in memory users (non-production)
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails danielUser = User.builder()
                .username("Daniel")
                .password(passwordEncoder.encode("password"))
                .roles("STUDENT")
                .build();

        UserDetails teacherUser = User.builder()
                .username("Mandala")
                .password(passwordEncoder.encode("password"))
                .roles("TEACHER")
                .build();

        return new InMemoryUserDetailsManager(danielUser, teacherUser);
    }

     */
}
