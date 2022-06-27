package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.27
 * 백준
 * Problem 11021
 * 두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.
 * 각 테스트 케이스마다 "Case #x: "를 출력한 다음, A+B를 출력한다. 테스트 케이스 번호는 1부터 시작한다.
 */
public class BaekJoon11021 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(br.readLine());
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int result = Integer.valueOf(st.nextToken()) + Integer.valueOf(st.nextToken());
            sb.append(String.format("Case #%d: %d", i, result)).append("\n");
        }
        System.out.println(sb.toString());
    }
}
