package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

public class BaekJoon10250 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int loopCnt = Integer.parseInt(br.readLine());
        for (int i = 0; i < loopCnt; i += 1) {
            st = new StringTokenizer(br.readLine(), " ");
            int H = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
            int N = Integer.parseInt(st.nextToken());

            if (N % H == 0) {
                sb.append((H * 100) + (N / H) + "\n");
            } else {
                sb.append((N % H) * 100 + ((N / H) + 1) + "\n");
            }
        }
        br.close();
        System.out.println(sb);
    }
}
