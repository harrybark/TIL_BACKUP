package programmers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/131701
 * @Date : 2022.11.06 (Mon)
 * @Author : 갈색토마토(@ka)
 * @Description : 연속 부분 수열 합의 개수
 *
 */
public class 연속부분수열합의개수 {

    private static final int[] INPUT_ELEMENT = new int[]{7,9,1,1,4} ;

    public static void main(String[] args) {
        연속부분수열합의개수 problem = new 연속부분수열합의개수();
        System.out.printf("The Answer is %d.", problem.solution(INPUT_ELEMENT));
    }

    public int solution(int[] elements) {

        int eLength = elements.length;

        int[] roulette = new int[eLength*2];
        System.arraycopy(elements, 0, roulette, 0, eLength);
        System.arraycopy(elements, 0, roulette, eLength, eLength);

        Set<Integer> basket = new HashSet<>();

        // 길이가 n인 연속 부분만큼 증가
        for(int i = 1 ; i <= eLength; i += 1) {
            for(int cur = 0 ; cur < eLength ; cur += 1) {
                basket.add( Arrays.stream(Arrays.copyOfRange(roulette, cur, cur + i)).sum());
            }
        }

        return basket.size();
    }
}
