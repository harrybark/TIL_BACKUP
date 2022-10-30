package me.devharrypmw.demospringsecurityform.config;

import me.devharrypmw.demospringsecurityform.account.AccountService;
import me.devharrypmw.demospringsecurityform.common.LoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Order(Ordered.LOWEST_PRECEDENCE - 50)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    public AccessDecisionManager accessDecisionManager() {

        // ROLE 계층 만들기
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        // Handler 셋팅
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        // voter 적용 Handler 셋팅
        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);
        List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList();
        return new AffirmativeBased(voters);
    }

    /**
     * Voter를 사용하지 않는 경우
     */
    public SecurityExpressionHandler accessDecisionManager_handler() {

        // ROLE 계층 만들기
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        // Handler 셋팅
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

        http
            .authorizeRequests()
                .mvcMatchers("/", "/info", "/account/**", "/signup/**").permitAll()
                .mvcMatchers("/admin").hasRole("ADMIN")
                // hasAuthority > hasRole : hasRole은 hasAuthority 하위
                .mvcMatchers("/user").hasAuthority("ROLE_USER")//.hasRole("USER")
                .anyRequest().authenticated()
                //.accessDecisionManager(accessDecisionManager())
                .expressionHandler(accessDecisionManager_handler())
        ;

        http.formLogin()
                .loginPage("/login")
                .permitAll()
        ;

        http.rememberMe()
                        .userDetailsService(accountService)
                                .key("remember-me-sample")
                ;
        http.httpBasic();

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                //.invalidateHttpSession(true)
                //.deleteCookies("")
                ;
        /*
        http.sessionManagement()
                    .sessionFixation()
                        .changeSessionId()
                    .maximumSessions(1)
                        .maxSessionsPreventsLogin(true)

                    // 주로 REST에서 쓰는 전략인데 웹에서는 문제가 생김.
                    //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        */

        // TODO ExceptionTranslationFilter -> FilterSecurityInterceptor(AccessDecisionManager, AffirmativeBased)
        // FilterSecurityInterceptor 처리 중 2가지 에러 발생
        // TODO AuthenticationException -> AuthenticationEntryPoint로 예외 처리(인증 페이지로 redirect)
        // TODO AccessDeniedException   -> AccessDeniedHandler 403 forbidden Error
        http.exceptionHandling()
                .accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> {
                    UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    String username = principal.getUsername();
                    System.out.println(username + " is denied to access " + httpServletRequest.getRequestURI());
                    httpServletResponse.sendRedirect("/access-denied");
                })
                //.accessDeniedPage("/access-denied")
                ;

    }

    /***
     * ignoring ( static resource ex:favicon )
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //web.ignoring().mvcMatchers("/favicon.ico");
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("harry").password("{noop}123").roles("USER")
                .and()
                .withUser("admin").password("{noop}!@#").roles("ADMIN");
    }
     */
}
