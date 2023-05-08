package ir.rashasoft.leavemanagement.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
        return new UsersDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()

                .antMatchers("/manager/leaveRequest/inProgress").authenticated()
                .antMatchers("/manager/leaveRequest/accept").authenticated()
                .antMatchers("/manager/leaveRequest/reject").authenticated()
                .antMatchers("/manager/leaveRequest/panel").authenticated()
                .antMatchers("/manager/leaveRequest/panel").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/manager/leaveRequest/inProgress")
                .defaultSuccessUrl("/manager/leaveRequest/accept")
                .defaultSuccessUrl("/manager/leaveRequest/reject")
                .defaultSuccessUrl("/manager/leaveRequest/panel")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
}
