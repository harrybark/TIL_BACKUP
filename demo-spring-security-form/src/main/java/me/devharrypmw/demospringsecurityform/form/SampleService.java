package me.devharrypmw.demospringsecurityform.form;

import me.devharrypmw.demospringsecurityform.account.Account;
import me.devharrypmw.demospringsecurityform.account.AccountContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {
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
}
