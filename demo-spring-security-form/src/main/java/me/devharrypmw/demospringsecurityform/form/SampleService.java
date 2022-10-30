package me.devharrypmw.demospringsecurityform.form;

import me.devharrypmw.demospringsecurityform.account.Account;
import me.devharrypmw.demospringsecurityform.account.AccountContext;
import me.devharrypmw.demospringsecurityform.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@Service
public class SampleService {
    // method 호출 이전에 권한 검사
    @Secured("ROLE_USER")
    //@RolesAllowed("ROLE_USER")
    // method 호출 이전에 권한 검사
    //@PreAuthorize("ROLE_USER")
    // method 호출 후 인가 검사
    //@PostAuthorize("ROLE_USER")
    public void dashboard() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Object principal = authentication.getPrincipal();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Object credentials = authentication.getCredentials();
        boolean authenticated = authentication.isAuthenticated();
        System.out.println("==========================");
        System.out.println(userDetails.getUsername());
        System.out.println("==========================");
        /*
        Account account = AccountContext.getAccount();
        System.out.println("==========================");
        System.out.println(account.getUsername());
        System.out.println("==========================");
        */
    }

    @Async
    public void asyncService() {
        SecurityLogger.log("Async Service");
        System.out.println("Async Service is called.");
    }
}
