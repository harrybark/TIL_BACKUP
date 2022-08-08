package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * @author Harry (@aka 갈색토마토)
 * @since 2022.06.27
 * 백준
 * Problem 11022
 * 두 정수 A와 B를 입력받은 다음, A+B를 출력하는 프로그램을 작성하시오.
 * 각 테스트 케이스마다 "Case #x: A + B = C" 형식으로 출력한다. x는 테스트 케이스 번호이고 1부터 시작하며, C는 A+B이다.
 */
public class BaekJoon11022 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int loopCnt = Integer.valueOf(br.readLine());
        StringTokenizer st;
        for (int i = 1; i <= loopCnt; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int num1 = Integer.valueOf(st.nextToken());
            int num2 = Integer.valueOf(st.nextToken());
            sb.append(String.format("Case #%d: %d + %d = %d", i, num1, num2, (num1 + num2))).append("\n");
        }
        br.close();
        System.out.println(sb.toString());
    }

}
