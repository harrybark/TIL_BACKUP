package me.devharrypmw.demospringsecurityform.form;

import me.devharrypmw.demospringsecurityform.account.Account;
import me.devharrypmw.demospringsecurityform.account.AccountService;
import me.devharrypmw.demospringsecurityform.account.WithNormalUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTest {

    @Autowired
    SampleService sampleService;

    @Autowired
    AccountService accountService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    // 아래 credential 설정은 아래의 어노테이션으로 대체 가능
    //@WithMockUser(...)
    //@WithNormalUser
    public void dashboard() throws Exception {
        // given

        Account account = new Account();
        account.setRole("ADMIN");
        account.setUsername("harry");
        account.setPassword("123");
        accountService.createNew(account);

        UserDetails userDetails = accountService.loadUserByUsername("harry");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "123");
        Authentication authenticate = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // when
        sampleService.dashboard();

        // then

    }
}