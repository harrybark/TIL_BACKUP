package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

public class BaekJoon15552 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int caseCount = Integer.parseInt(br.readLine());

        StringTokenizer st;

        for (int i = 0; i < caseCount; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken());
            bw.write((num1 + num2) + "\n");
        }
        br.close();
        bw.flush();
        bw.close();
    }
}
