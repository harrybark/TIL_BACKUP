package programmers;

import java.util.Arrays;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/86051
 * @Date : 2022.11.20 (Mon)
 * @Author : 갈색토마토(@ka)
 * @Description : 없는 숫자 더하기
 */
public class 없는숫자더하기 {

    private static final int[] INPUT_NUMBER1 = {1, 2, 3, 4, 6, 7, 8, 0};
    private static final int[] INPUT_NUMBER2 = {5, 8, 4, 0, 6, 7, 9};

    public static int solution(int[] numbers) {
        int answer = 0;

        boolean[] basket = new boolean[10];
        for(int i = 0 ; i < numbers.length; i += 1) {
            basket[numbers[i]] = true;
        }

        for(int i = 0 ; i < basket.length ; i += 1) {
            if(basket[i]) continue;
            answer += i;
        }
        return answer;
    }

    public static void main(String[] args) {
        System.out.println(없는숫자더하기.solution(INPUT_NUMBER1));
        System.out.println(없는숫자더하기.solution(INPUT_NUMBER2));
    }
}
