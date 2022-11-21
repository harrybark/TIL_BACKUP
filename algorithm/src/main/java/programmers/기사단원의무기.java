package programmers;

import java.util.*;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/136798
 * @Date : 2022.11.20 (Mon)
 * @Author : 갈색토마토(@ka)
 * @Description : 기사단원의 무기
 */
public class 기사단원의무기 {
    public static void main(String[] args) {
        System.out.println(new 기사단원의무기().solution(10, 3, 2));
    }
    public int solution(int number, int limit, int power) {
        int answer = 0;
        int[] basket = new int[number];

        for( int i = 1 ; i <= number ; i += 1 ) {
            int temp = getDivisorCnt(i);
            basket[i-1] = temp > limit ? power : temp;
        }

        answer = Arrays.stream(basket).sum();
        return answer;
    }

    private int getDivisorCnt(int n) {
        if ( n == 1 ) return 1;
        else if ( n < 4 ) return 2;

        int sqrt = (int)Math.sqrt(n);

        Set<Integer> list = new HashSet<>();
        for (int i = 1 ; i <= sqrt; i += 1) {
            if( n % i == 0 ) {
                if( Math.pow(i, 2) == 0 ) {
                    list.add(i);
                } else {
                    list.add(i);
                    list.add(n/i);
                }
            }
        }
        return list.size();
    }

}
