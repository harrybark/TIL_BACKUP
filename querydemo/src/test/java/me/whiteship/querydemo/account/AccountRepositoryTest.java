package me.whiteship.querydemo.account;

import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static me.whiteship.querydemo.account.QAccount.account;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AccountRepositoryTest {

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void crud() throws Exception {
        // given
        Predicate predicate = account
                        .firstName.containsIgnoreCase("keesun")
                        .and(account.lastName.startsWith("baek"));


        // when
        Optional<Account> findOne = accountRepository.findOne(predicate);

        // then
        assertThat(findOne).isEmpty();
    }
}