package programmers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class 숫자문자열과영단어Test {

    @BeforeAll
    static void beforeAll() {
        System.out.println("## 테스트 동작 ##");
    }

    @Test
    @DisplayName(value = "유효한 값 확인")
    public void 동작을_확인한다() throws Exception {
        // given
        숫자문자열과영단어 x = new 숫자문자열과영단어();
        String first_input = new String("one4seveneight");

        // when
        int answer = x.solution(first_input);

        // then
        assertEquals(1478, answer, "valid");
    }
}