package com.et.eatingtogether.security;

import com.et.eatingtogether.service.CustomerUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass = true)
@Deprecated
public class MultipleSecurityConfig {



    // spring security에서 허용할 web 리소스 path
    public static final String[] SECURITY_EXCLUDE_PATTERN_ARR = {
    };

    @Configuration
    @Order(1)
    public static class CustomerSecurityConfigure extends WebSecurityConfigurerAdapter {

        private CustomerUserService cus;

        @Override
        public void configure(WebSecurity web) {
            // /css/**, /images/**, /js/** 등 정적 리소스는 보안필터를 거치지 않게 한다.
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception{
            http
                    .antMatcher("/admin/**")
                    .authorizeRequests()
                    .anyRequest().hasRole("ADMIN")
                    .and()
                    .httpBasic();
                    // 제한 없음 설정
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/**").permitAll()
//                .antMatchers("/**").permitAll()
//                .antMatchers().permitAll() // 나머지는 모두 인증 요청
//                    .anyRequest().permitAll()
//                    .and()
//                    .formLogin() // customer
//                    .loginPage("/usual/customerLogin") // 로그인 폼
//                    .loginProcessingUrl("/usual/customerLogin") // 로그인 처리
//                    .defaultSuccessUrl("/")// 로그인 결과
//                    .permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/usual/slogin")
//                .loginProcessingUrl("/usual/slogin")
//                .defaultSuccessUrl("store/categoryMain")
//                    .and()
//                    .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/usual/logout"))
//                    .logoutSuccessUrl("/")
//                    .invalidateHttpSession(true);

        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(cus).passwordEncoder(passwordEncoder());
        }

    }

    @Configuration
    public static class StoreSecurityConfigure extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) {
            // /css/**, /images/**, /js/** 등 정적 리소스는 보안필터를 거치지 않게 한다.
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }

        @Override
        public void configure(HttpSecurity http) throws Exception{
            http.authorizeRequests()
                    .anyRequest().authenticated()
                    .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                        public <O extends FilterSecurityInterceptor> O postProcess(
                                O fsi) {
                            fsi.setPublishAuthorizationSuccess(true);
                            return fsi;
                        }
                    });
                    // 제한 없음 설정
//                    .antMatchers("/admin/**").hasRole("ADMIN")
//                    .antMatchers("/**").permitAll()
//                    .anyRequest().permitAll()
//                .antMatchers("/**").permitAll()
//                .antMatchers().permitAll() // 나머지는 모두 인증 요청
//                    .and()
//                    .formLogin() // customer
//                    .loginPage("/usual/customerLogin") // 로그인 폼
//                    .loginProcessingUrl("/usual/customerLogin") // 로그인 처리
//                    .defaultSuccessUrl("/")// 로그인 결과
//                    .permitAll()
//                .and()
//                .formLogin()
//                .loginPage("/usual/slogin")
//                .loginProcessingUrl("/usual/slogin")
//                .defaultSuccessUrl("store/categoryMain")
//                    .and()
//                    .logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/usual/logout"))
//                    .logoutSuccessUrl("/")
//                    .invalidateHttpSession(true);

        }

//        @Override
//        public void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.userDetailsService(cus).passwordEncoder(passwordEncoder());
//        }
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }




}
