package baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaekJoon2741 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int loopCnt = Integer.valueOf(br.readLine());
        StringBuilder sb = new StringBuilder();
        int n = 1;
        while(loopCnt-- > 0) {
            sb.append(n++).append("\n");
        }
        br.close();
        System.out.println(sb.toString());
    }
}
