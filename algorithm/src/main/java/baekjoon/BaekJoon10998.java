package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.26
 * 백준
 * Problem 10998
 * 두 정수 A와 B를 입력받은 다음, A×B를 출력하는 프로그램을 작성하시오.
 * 첫째 줄에 A와 B가 주어진다. (0 < A, B < 10)
 */
public class BaekJoon10998 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        StringTokenizer stringTokenizer = new StringTokenizer(input, " ");
        int num1 = Integer.parseInt(stringTokenizer.nextToken());
        int num2 = Integer.parseInt(stringTokenizer.nextToken());

        System.out.println(multiply(num1, num2));
    }

    public static int multiply(int num1, int num2) {
        return num1 * num2 ;
    }
}
