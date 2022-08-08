package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.27
 * 백준
 * Problem 2438
 * 첫째 줄에는 별 1개, 둘째 줄에는 별 2개, N번째 줄에는 별 N개를 찍는 문제
 */
public class BaekJoon2438 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int loopCnt = Integer.valueOf(br.readLine());

        for (int i = 1; i <= loopCnt; i++) {
            for (int j = 1; j <= i; j++) {
                sb.append("*");
            }
            sb.append("\n");
        }
        br.close();
        System.out.println(sb.toString());
    }
}
