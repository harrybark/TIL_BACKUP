package baekjoon;

import java.io.*;

public class BaekJoon11653 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringBuffer sb = new StringBuffer();
        int N = Integer.parseInt(br.readLine());
        br.close();
        for (int i = 2; i <= Math.sqrt(N); i++) {
            while (N % i == 0) {
                sb.append(i).append("\n");
                N /= i;
            }
        }

        // 1인 경우 아무것도 출력하지 않으므로 분기 추가
        if (N != 1) {
            sb.append(N);
        }
        bw.write(sb.toString());
        bw.close();
    }
}
