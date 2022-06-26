package baekjoon;

import java.util.Scanner;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.26
 * 백준
 * Problem 1001
 * 두 정수 A와 B를 입력받은 다음, A-B를 출력하는 프로그램을 작성하시오.
 * 첫째 줄에 A와 B가 주어진다. (0 < A, B < 10)
 */
public class BaekJoon1001 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int num1 = sc.nextInt();
        int num2 = sc.nextInt();
        System.out.println(minus(num1, num2));
    }

    private static int minus(int num1, int num2) {
        return num1 - num2 ;
    }
}
