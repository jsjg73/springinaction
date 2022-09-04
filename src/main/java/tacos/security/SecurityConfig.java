package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 사용자의 HTTP 요청 경로에 대해 접근 제한과 같은 보안 관련 처리를 우리가 원하는 대로 할 수 있게 해준다.
    @Override
    protected void configure(HttpSecurity http) throws  Exception {// 보안 정보 구성하는 메서드
        // 사용자 등록을 위한 페이지와 인증된 사용자를 위한 페이지에 다른 접근 권한을 설정한다.

        http
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").access("permitAll")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/authenticate")
                .usernameParameter("user")
                .passwordParameter("pwd")
                .defaultSuccessUrl("/design")

                .and()
                .logout()
                .logoutSuccessUrl("/")

                .and()
                .csrf();

        // 표현식을 사용할 때 장점.
        // '화요일의 타코 생성은 ROLE_USER 권한을 갖는 사용자에게만 허용하고 싶다'는 가상의 스토리
//        http
//            .authorizeRequests()
//                .antMatchers("/design", "/orders")
//                    .access("hasRole('ROLE_USE') && T(java.util.Calendar).getInstance().get(T(java.util.Calendar).DAY_OF_WEEK) == T(java.util.Calendar).TUESDAY")
//            .antMatchers("/", "/**").access("permtiAll");
    }

//    @Autowired
//    DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception { // 사용자 인증 정보 구성하는 메서드
//        auth
//          .jdbcAuthentication()
//          .dataSource(dataSource)
//          .usersByUsernameQuery(
//                  "SELECT username, password, enabled FROM users WHERE username=?")
//          .authoritiesByUsernameQuery(
//                  "SELECT username, authority FROM authorities WHERE username=?")
//          .passwordEncoder(new NoEncodingPasswordEncoder());

        auth
          .userDetailsService(userDetailsService)
          .passwordEncoder(encoder());
    }
}
