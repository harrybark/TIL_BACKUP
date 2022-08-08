package baekjoon;

import java.io.*;
import java.util.StringTokenizer;

public class BaekJoon10950 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int caseCnt = Integer.parseInt(br.readLine());
        for (int i = 0; i < caseCnt; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine(), " ");
            int num1 = Integer.parseInt(st.nextToken());
            int num2 = Integer.parseInt(st.nextToken());
            System.out.println(num1 + num2);
        }
    }
}
