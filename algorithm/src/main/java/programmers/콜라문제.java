package programmers;

/***
 * @URL : https://school.programmers.co.kr/learn/courses/30/lessons/132267
 * @Date : 2022.11.20 (Sun)
 * @Author : 갈색토마토(@ka)
 * @Description : 콜라문제
 *
 */
public class 콜라문제 {

    public static void main(String[] args) {
        System.out.println(콜라문제.solution(2, 1, 20));
        System.out.println(콜라문제.solution(3, 1, 20));
    }

    public static int solution(int a, int b, int n) {
        int answer = 0;
        int mok = 0, na = 0;
        while(true) {
            mok = (n / a) * b; // 최대값 * b개를 돌려 받음
            na = n % a;
            n = mok + na ;
            answer += mok;
            if ( n < a ) {
                break;
            }

        }

        return answer;
    }
}
