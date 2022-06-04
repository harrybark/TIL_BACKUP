package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    // 테스트에 @Transactional 이있으면 테스트가 수행 후 자동으로 db rollback
    // DB에서 확인하고 싶은 경우 @Rollback false를 사용하면 된다.
    public void 저장을_확인한다() throws Exception{
        // given
        Member member = new Member();
        member.setUsername("MemberA");

        // when
        Long save = memberRepository.save(member);
        
        // then
        Member findMember = memberRepository.find(save);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        // 객체간의 비교 ( == )
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}