package baekjoon;

import java.io.*;

public class BaekJoon8393 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int maxNum = Integer.parseInt(br.readLine());
        int sum = 0;
        for (int i = 1; i <= maxNum; i++) {
            sum += i;
        }
        System.out.println(sum);
    }
}
