package programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/142086
 * @Date : 2022.12.15 (Thur)
 * @Author : 갈색토마토(@ka)
 * @Description : 가장 가까운 같은 글자
 */
public class TheMostClosestChar {

    public static void main(String[] args) {
        TheMostClosestChar t = new TheMostClosestChar();
        int[] answer = t.solution("banana");
        System.out.println(Arrays.toString(answer));
    }

    public int[] solution(String s) {
        int[] answer = new int[s.length()];
        Arrays.fill(answer, -1);

        List<String> list = Arrays.asList(s.split(""));
        for(int i = list.size() - 1; i >= 0 ; i -= 1) {

            int k = s.lastIndexOf(list.get(i), i-1);
            answer[i] = k < 0 ? k : i - k;
        }

        return answer;
    }
}
