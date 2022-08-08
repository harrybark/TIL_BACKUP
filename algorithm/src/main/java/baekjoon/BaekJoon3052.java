package baekjoon;

import java.io.*;

public class BaekJoon3052 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean[] arr = new boolean[42];

        for (int i = 0; i < 10; i++) {
            arr[Integer.parseInt(br.readLine()) % 42] = true;
        }
        int cnt = 0;
        for (boolean isTrue : arr) {
            if (!isTrue) continue;

            cnt++;
        }
        System.out.println(cnt);
    }

}
